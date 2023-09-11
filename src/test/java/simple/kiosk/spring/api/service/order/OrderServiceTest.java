package simple.kiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.response.OrderResponse;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;

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

	private static Product createProduct(String productNumber, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(HANDMADE)
			.sellingStatus(SELLING)
			.name(name)
			.price(price)
			.build();
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
}
