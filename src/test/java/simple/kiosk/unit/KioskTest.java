package simple.kiosk.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import simple.kiosk.unit.beverage.Americano;
import simple.kiosk.unit.beverage.Latte;
import simple.kiosk.unit.order.Order;

class KioskTest {

	@Test
	void add() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Americano());

		assertThat(kiosk.getBeverages()).hasSize(1);
		assertThat(kiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
		assertThat(kiosk.getBeverages().get(0).getPrice()).isEqualTo(2000);
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
	void createOrder() {
		Kiosk kiosk = new Kiosk();
		kiosk.add(new Latte());
		kiosk.add(new Americano());

		Order order = kiosk.createOrder();
		assertThat(order.getBeverages().get(0).getName()).isEqualTo("카페라떼");
		assertThat(order.getBeverages().get(0).getPrice()).isEqualTo(3500);
		assertThat(order.getBeverages().get(1).getName()).isEqualTo("아메리카노");
		assertThat(order.getBeverages().get(1).getPrice()).isEqualTo(2000);
	}

}