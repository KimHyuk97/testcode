package simple.kiosk.spring.domain.orderproduct;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.domain.BaseEntity;
import simple.kiosk.spring.domain.order.Order;
import simple.kiosk.spring.domain.product.Product;

/**
 * packageName    : simple.kiosk.spring.domain.order
 * fileName       : OrderProduct
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * --------------------------------------------
 * ---------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProduct extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	private Product product;

	public OrderProduct(Order order, Product product) {
		this.order = order;
		this.product = product;
	}
}
