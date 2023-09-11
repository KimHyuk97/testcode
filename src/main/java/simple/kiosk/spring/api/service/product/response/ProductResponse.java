package simple.kiosk.spring.api.service.product.response;

import lombok.Builder;
import lombok.Getter;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductSellingStatus;
import simple.kiosk.spring.domain.product.ProductType;

/**
 * packageName    : simple.kiosk.spring.api.service.product.response
 * fileName       : ProductResponse
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Getter
public class ProductResponse {

	private Long id;

	private String productNumber;

	private ProductType type;

	private ProductSellingStatus sellingStatus;

	private String name;

	private int price;

	@Builder
	private ProductResponse(Long id, String productNumber, ProductType type, ProductSellingStatus sellingStatus,
		String name,
		int price) {
		this.id = id;
		this.productNumber = productNumber;
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}

	public static ProductResponse of(Product product) {
		return ProductResponse.builder()
			.id(product.getId())
			.productNumber(product.getProductNumber())
			.type(product.getType())
			.sellingStatus(product.getSellingStatus())
			.name(product.getName())
			.price(product.getPrice())
			.build();
	}

}
