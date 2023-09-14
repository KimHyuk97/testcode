package simple.kiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.response.OrderResponse;
import simple.kiosk.spring.domain.order.Order;
import simple.kiosk.spring.domain.order.OrderRepository;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;
import simple.kiosk.spring.domain.product.ProductType;
import simple.kiosk.spring.domain.stock.Stock;
import simple.kiosk.spring.domain.stock.StockRepository;

/**
 * packageName    : simple.kiosk.spring.api.service.product
 * fileName       : OrderService
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;

	private final ProductRepository productRepository;

	private final StockRepository stockRepository;

	/**
	 * 재고 감소 -> 동시성 고민
	 * optimistic lock / pessimistic lock / ...
	 */
	@Transactional
	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> products = findProductsBy(productNumbers);

		deductStockQuantities(products);

		Order order = Order.create(products, registeredDateTime);
		Order savedOrder = orderRepository.save(order);
		return OrderResponse.of(savedOrder);
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));

		return productNumbers.stream()
			.map(productMap::get)
			.toList();
	}

	private void deductStockQuantities(List<Product> products) {

		List<String> stockProductNumbers = extractStockProductNumbers(products);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);

		Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

		// 재고 차감 시도
		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();

			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException(stockProductNumber + "의 재고가 부족합니다.");
			}

			stock.deductQuantity(quantity);
		}
	}

	private List<String> extractStockProductNumbers(List<Product> products) {
		return products.stream()
			.filter(p -> ProductType.containsStockType(p.getType()))
			.map(Product::getProductNumber)
			.toList();
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {

		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

		return stocks.stream()
			.collect(Collectors.toMap(Stock::getProductNumber, s -> s));
	}

	private Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}

}
