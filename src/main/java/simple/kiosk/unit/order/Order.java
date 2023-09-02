package simple.kiosk.unit.order;

import java.time.LocalDateTime;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import simple.kiosk.unit.beverage.Beverage;

@Getter
@RequiredArgsConstructor
public class Order {

	private final List<Beverage> beverages;

	private final LocalDateTime orderDateTime;

}
