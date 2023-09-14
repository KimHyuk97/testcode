package simple.kiosk.spring.domain.stock;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : simple.kiosk.spring.domain.stock
 * fileName       : StockTest
 * author         : Hyuk Kim
 * date           : 2023-09-15
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-15        Hyuk Kim       최초 생성
 */
@SpringBootTest
@ActiveProfiles("test")
class StockTest {

	@Test
	@DisplayName("재고의 수량이 제공된 수량보다 작은지 확인한다.")
	void isQuantityLessThan() {

		Stock stock = Stock.create("001", 1);
		int quantity = 2;

		boolean result = stock.isQuantityLessThan(quantity);

		Assertions.assertThat(result).isTrue();
	}

	@Test
	@DisplayName("재고를 주어진 개수만큼 차감할 수 있다.")
	void deductQuantity() {

		Stock stock = Stock.create("001", 2);
		int quantity = 2;

		stock.deductQuantity(quantity);

		Assertions.assertThat(stock.getQuantity()).isZero();
	}

	@Test
	@DisplayName("재고보다 많은 수량으로 차감 시도하는 경우 예외를 발생한다.")
	void deductQuantityExecution() {

		Stock stock = Stock.create("001", 2);
		int quantity = 3;

		Assertions.assertThatThrownBy(() -> stock.deductQuantity(quantity))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("차감할 재고 수량이 없습니다.");
	}
}
