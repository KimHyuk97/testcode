package simple.kiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import simple.kiosk.spring.api.client.mail.MailSendClient;
import simple.kiosk.spring.domain.history.mail.MailSendHistory;
import simple.kiosk.spring.domain.history.mail.MailSendHistoryRepository;
import simple.kiosk.spring.domain.order.Order;
import simple.kiosk.spring.domain.order.OrderRepository;
import simple.kiosk.spring.domain.order.OrderStatus;
import simple.kiosk.spring.domain.orderproduct.OrderProductRepository;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;

/**
 * packageName    : simple.kiosk.spring.api.service.order
 * fileName       : OrderStatisticsServiceTest
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
@SpringBootTest
@ActiveProfiles("test")
class OrderStatisticsServiceTest {

	@Autowired
	private OrderStatisticsService orderStatisticsService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;

	@MockBean
	private MailSendClient mailSendClient;

	@AfterEach
	void tearDown() {
		mailSendHistoryRepository.deleteAllInBatch();
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("결제 완료된 주문들을 조회하여 매출 통계 메일을 전송한다.")
	void sendOrderStatisticsMail() {
		// given
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 3000);
		Product product3 = createProduct("003", "우유", 2000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Order order1 = createOrder(List.of(product1), LocalDateTime.now());
		Order order2 = createOrder(List.of(product2), LocalDateTime.now());
		Order order3 = createOrder(List.of(product3), LocalDateTime.now());
		orderRepository.saveAll(List.of(order1, order2, order3));

		// stubbing
		Mockito.when(
				mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
			.thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.now(), "wgilooy97@naver.com");

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> mailSendHistories = mailSendHistoryRepository.findAll();
		assertThat(mailSendHistories).hasSize(1)
			.extracting("content")
			.contains("총 매출 합계는 7000원입니다.");

		// assertThatThrownBy(() -> orderStatisticsService.sendOrderStatisticsMail(LocalDate.now(), "wgilooy97@naver.com"))
		// 	.isInstanceOf(IllegalArgumentException.class)
		// 	.hasMessage("메일 전송");
	}

	private Product createProduct(String productNumber, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.name(name)
			.price(price)
			.build();
	}

	private Order createOrder(List<Product> products, LocalDateTime orderDateTime) {
		return Order.builder()
			.products(products)
			.registeredDateTime(orderDateTime)
			.orderStatus(OrderStatus.PAYMENT_COMPLETED)
			.build();
	}
}
