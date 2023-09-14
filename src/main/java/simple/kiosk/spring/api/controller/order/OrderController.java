package simple.kiosk.spring.api.controller.order;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.OrderService;
import simple.kiosk.spring.api.service.order.response.OrderResponse;

/**
 * packageName    : simple.kiosk.spring.api.controller.product
 * fileName       : OrderController
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class OrderController {

	private final OrderService orderService;

	@PostMapping("/api/v1/orders/new")
	public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
		return orderService.createOrder(request, LocalDateTime.now());
	}

}
