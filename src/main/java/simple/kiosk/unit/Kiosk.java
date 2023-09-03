package simple.kiosk.unit;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Getter;
import simple.kiosk.unit.beverage.Beverage;
import simple.kiosk.unit.order.Order;

@Getter
public class Kiosk {

	private static final LocalTime SHOT_OPEN_TIME = LocalTime.of(10, 0);
	private static final LocalTime SHOT_CLOSE_TIME = LocalTime.of(22, 0);

	private final List<Beverage> beverages = new ArrayList<>();

	public void add(Beverage beverage) {
		beverages.add(beverage);
	}

	public void add(Beverage beverage, int count) {
		if (count <= 0) {
			throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
		}
		IntStream.rangeClosed(1, count).forEach(i -> beverages.add(beverage));
	}

	public void remove(Beverage beverage) {
		beverages.remove(beverage);
	}

	public void clear() {
		beverages.clear();
	}

	public int calculateTotalPrice() {
		return beverages.stream()
			.mapToInt(Beverage::getPrice)
			.sum();
	}

	public Order createOrder() {
		LocalDateTime currentDateTime = LocalDateTime.now();
		LocalTime currentTime = currentDateTime.toLocalTime();

		if (currentTime.isBefore(SHOT_OPEN_TIME) || currentTime.isAfter(SHOT_CLOSE_TIME)) {
			throw new IllegalArgumentException("죄송합니다. 영업 시간은 10 ~ 22시입니다.");
		}

		return new Order(beverages, currentDateTime);
	}

	public Order createOrder(LocalDateTime currentDateTime) {
		LocalTime currentTime = currentDateTime.toLocalTime();

		if (currentTime.isBefore(SHOT_OPEN_TIME) || currentTime.isAfter(SHOT_CLOSE_TIME)) {
			throw new IllegalArgumentException("죄송합니다. 영업 시간은 10 ~ 22시입니다.");
		}

		return new Order(beverages, currentDateTime);
	}
}
