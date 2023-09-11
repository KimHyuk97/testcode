package simple.kiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simple.kiosk.spring.domain.product.Product;

/**
 * packageName    : simple.kiosk.spring.domain.order
 * fileName       : OrderTest
 * author         : Hyuk Kim
 * date           : 2023-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-12        Hyuk Kim       최초 생성
 */
class OrderTest {

	@Test
	@DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
	void calculateTotalPrice() {
		// given
		List<Product> products = List.of(
			createProduct("001", 1000),
			createProduct("002", 2000)
		);

		LocalDateTime localDateTime = LocalDateTime.now();

		// when
		Order order = Order.create(products, localDateTime);

		// then
		assertThat(order.getTotalPrice()).isEqualTo(3000);

		assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);

		assertThat(order.getRegisteredDateTime()).isEqualTo(localDateTime);
	}

	private Product createProduct(String productNumber, int price) {
		return Product.builder()
			.type(HANDMADE)
			.productNumber(productNumber)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}
