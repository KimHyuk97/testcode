package simple.kiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.response.OrderResponse;
import simple.kiosk.spring.domain.order.OrderRepository;
import simple.kiosk.spring.domain.orderproduct.OrderProductRepository;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;
import simple.kiosk.spring.domain.stock.Stock;
import simple.kiosk.spring.domain.stock.StockRepository;

/**
 * packageName    : simple.kiosk.spring.api.service.order
 * fileName       : OrderServiceTest
 * author         : Hyuk Kim
 * date           : 2023-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-12        Hyuk Kim       최초 생성
 */
@ActiveProfiles("test")
@SpringBootTest
class OrderServiceTest {

	@Autowired
	private OrderService orderService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private StockRepository stockRepository;

	private static Product createProduct(String productNumber, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(BAKERY)
			.sellingStatus(SELLING)
			.name(name)
			.price(price)
			.build();
	}

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
	void createOrder() {
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 2500);
		Product product3 = createProduct("003", "빙수", 5000);

		productRepository.saveAll(List.of(product1, product2, product3));

		LocalDateTime registeredDateTime = LocalDateTime.now();

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "002"))
			.build();

		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 4500);
		assertThat(response.getProducts()).hasSize(2)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 2000),
				tuple("002", 2500)
			);
	}

	@Test
	@DisplayName("주문번호 재고 확인")
	void test() {
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 2500);
		Product product3 = createProduct("003", "빙수", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock americanoStock = Stock.create("001", 2);
		Stock letteStock = Stock.create("002", 2);
		Stock bingsuStock = Stock.create("003", 2);
		stockRepository.saveAll(List.of(americanoStock, letteStock, bingsuStock));

		LocalDateTime registeredDateTime = LocalDateTime.now();

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001", "002", "003"))
			.build();

		OrderResponse response = orderService.createOrder(request, registeredDateTime);

		assertThat(response.getId()).isNotNull();
		assertThat(response)
			.extracting("registeredDateTime", "totalPrice")
			.contains(registeredDateTime, 11500);
		assertThat(response.getProducts()).hasSize(4)
			.extracting("productNumber", "price")
			.containsExactlyInAnyOrder(
				tuple("001", 2000),
				tuple("001", 2000),
				tuple("002", 2500),
				tuple("003", 5000)
			);

		List<Stock> stocks = stockRepository.findAll();
		assertThat(stocks).hasSize(3)
			.extracting("productNumber", "quantity")
			.containsExactlyInAnyOrder(
				tuple("001", 0),
				tuple("002", 1),
				tuple("003", 1)
			);

	}

	@Test
	@DisplayName("재고가 부족한 상품으로 주문을 생성하려는 경우 예외가 발생한다.")
	void testException() {
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 2500);
		Product product3 = createProduct("003", "빙수", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock americanoStock = Stock.create("001", 2);
		Stock letteStock = Stock.create("002", 2);
		Stock bingsuStock = Stock.create("003", 0);
		stockRepository.saveAll(List.of(americanoStock, letteStock, bingsuStock));

		LocalDateTime registeredDateTime = LocalDateTime.now();

		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001", "001", "002", "003"))
			.build();

		assertThatThrownBy(() -> orderService.createOrder(request, registeredDateTime))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("003의 재고가 부족합니다.");

	}
}
