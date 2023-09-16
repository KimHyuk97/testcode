package simple.kiosk.spring.domain.product;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.api.controller.product.request.ProductCreateRequest;
import simple.kiosk.spring.domain.BaseEntity;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : Product
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
public class Product extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String productNumber;

	@Enumerated(EnumType.STRING)
	private ProductType type;

	@Enumerated(EnumType.STRING)
	private ProductSellingStatus sellingStatus;

	private String name;

	private int price;

	@Builder
	private Product(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name,
		int price) {

		this.productNumber = productNumber;
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public static Product createProduct(String productNumber, ProductCreateRequest request) {
		return Product.builder()
			.productNumber(productNumber)
			.type(request.getType())
			.sellingStatus(request.getSellingStatus())
			.name(request.getName())
			.price(request.getPrice())
			.build();
	}
}
