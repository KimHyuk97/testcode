package simple.kiosk.spring.domain.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.domain.BaseEntity;
import simple.kiosk.spring.domain.orderproduct.OrderProduct;
import simple.kiosk.spring.domain.product.Product;

/**
 * packageName    : simple.kiosk.spring.domain.order
 * fileName       : Order
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;

	private int totalPrice;

	private LocalDateTime registeredDateTime;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderProduct> orderProducts = new ArrayList<>();

	public Order(List<Product> products, LocalDateTime registeredDateTime) {
		this.orderStatus = OrderStatus.INIT;
		this.totalPrice = calculateTotalPrice(products);
		this.registeredDateTime = registeredDateTime;
		this.orderProducts = products.stream()
			.map(product -> new OrderProduct(this, product))
			.toList();
	}

	public static Order create(List<Product> products, LocalDateTime registeredDateTime) {
		return new Order(products, registeredDateTime);
	}

	private int calculateTotalPrice(List<Product> products) {
		return products.stream()
			.mapToInt(Product::getPrice)
			.sum();
	}
}
