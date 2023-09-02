package simple.kiosk.unit;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import simple.kiosk.unit.beverage.Beverage;
import simple.kiosk.unit.order.Order;

@Getter
public class Kiosk {

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		int totalPrice = 0;
		for (Beverage beverage : beverages) {
			totalPrice += beverage.getPrice();
		}
		return totalPrice;
	}

	public Order createOrder() {
		return new Order(beverages, LocalDateTime.now());
	}
}
