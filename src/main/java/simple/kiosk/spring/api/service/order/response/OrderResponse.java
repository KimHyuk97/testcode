package simple.kiosk.spring.api.service.order.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import simple.kiosk.spring.api.service.product.response.ProductResponse;
import simple.kiosk.spring.domain.order.Order;

/**
 * packageName    : simple.kiosk.spring.api.service.order
 * fileName       : OrderResponse
 * author         : Hyuk Kim
 * date           : 2023-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-12        Hyuk Kim       최초 생성
 */
@Getter
public class OrderResponse {

	private Long id;
	private int totalPrice;
	private LocalDateTime registeredDateTime;
	private List<ProductResponse> products;

	@Builder
	public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> products) {
		this.id = id;
		this.totalPrice = totalPrice;
		this.registeredDateTime = registeredDateTime;
		this.products = products;
	}

	public static OrderResponse of(Order order) {
		return OrderResponse.builder()
			.id(order.getId())
			.products(order.getOrderProducts().stream()
				.map(orderProduct -> ProductResponse.of(orderProduct.getProduct()))
				.toList()
			)
			.registeredDateTime(order.getRegisteredDateTime())
			.totalPrice(order.getTotalPrice())
			.build();
	}
}
