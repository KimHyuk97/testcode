package simple.kiosk.unit;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import simple.kiosk.unit.beverage.Americano;
import simple.kiosk.unit.beverage.Latte;
import simple.kiosk.unit.order.Order;

class KioskTest {

	@Test
	@DisplayName("음료 1개를 주문 목록에 추가한다.")
	void add() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Americano());

		assertThat(kiosk.getBeverages()).hasSize(1);
		assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
		assertThat(kiosk.getBeverages().get(0).getPrice()).isEqualTo(2000);
	}

	@Test
	@DisplayName("음료 여러개를 주문 목록에 추가한다")
	void addSeveralBeverages() {
		Kiosk kiosk = new Kiosk();
		Americano americano = new Americano();

		kiosk.add(americano, 4);

		assertThat(kiosk.getBeverages()).hasSize(4);
		assertThat(kiosk.getBeverages().get(0)).isEqualTo(americano);
		assertThat(kiosk.getBeverages().get(1)).isEqualTo(americano);
		assertThat(kiosk.getBeverages().get(2)).isEqualTo(americano);
		assertThat(kiosk.getBeverages().get(3)).isEqualTo(americano);
	}

	@Test
	@DisplayName("음료 0개 이하를 주문하면 주문 목록에 추가하지 못한다.")
	void addZeroBeverages() {
		Kiosk kiosk = new Kiosk();
		Americano americano = new Americano();

		assertThatThrownBy(() -> kiosk.add(americano, 0))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
	}

	@Test
	@DisplayName("주문 목록에 담긴 음료들의 총 금액을 계산하다.")
	void calculateTotalPrice() {
		// given
		Kiosk kiosk = new Kiosk();
		Americano americano = new Americano();
		Latte latte = new Latte();

		kiosk.add(americano);
		kiosk.add(latte);

		// when
		int totalPrice = kiosk.calculateTotalPrice();

		// then
		assertThat(totalPrice).isEqualTo(5500);
	}

	@Test
	void remove() {
		Kiosk kiosk = new Kiosk();
		Americano americano = new Americano();

		kiosk.add(americano);
		assertThat(kiosk.getBeverages()).hasSize(1);

		kiosk.remove(americano);
		assertThat(kiosk.getBeverages()).isEmpty();
	}

	@Test
	void clear() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Latte());
		kiosk.add(new Americano());

		kiosk.clear();
		assertThat(kiosk.getBeverages()).isEmpty();
	}

	@Test
	void createOrderOutsideOpenTime() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Latte(), 3);
		kiosk.add(new Americano());

		LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 3, 23, 0);

		assertThatThrownBy(() -> kiosk.createOrder(localDateTime))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("죄송합니다. 영업 시간은 10 ~ 22시입니다.");
	}

	@Test
	void createOrderInsideOpenTime() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Latte());
		kiosk.add(new Americano());

		LocalDateTime localDateTime = LocalDateTime.of(2023, 9, 3, 10, 0);

		Order order = kiosk.createOrder(localDateTime);
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("카페라떼");
		assertThat(order.getBeverages().get(0).getPrice()).isEqualTo(3500);
		assertThat(order.getBeverages().get(1).getName()).isEqualTo("아메리카노");
		assertThat(order.getBeverages().get(1).getPrice()).isEqualTo(2000);
	}

}