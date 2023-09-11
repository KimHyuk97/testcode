package simple.kiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.response.OrderResponse;
import simple.kiosk.spring.domain.order.Order;
import simple.kiosk.spring.domain.order.OrderRepository;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;

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

	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {

		List<String> productNumbers = request.getProductNumbers();

		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		Order order = Order.create(products, registeredDateTime);

		Order savedOrder = orderRepository.save(order);

		return OrderResponse.of(savedOrder);
	}

}
