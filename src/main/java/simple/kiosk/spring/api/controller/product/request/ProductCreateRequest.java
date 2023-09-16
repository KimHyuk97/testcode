package simple.kiosk.spring.api.controller.product.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.domain.product.ProductSellingStatus;
import simple.kiosk.spring.domain.product.ProductType;

/**
 * packageName    : simple.kiosk.spring.api.controller.product.request
 * fileName       : ProductCreateRequest
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@Getter
@NoArgsConstructor
public class ProductCreateRequest {

	private ProductType type;

	private ProductSellingStatus sellingStatus;

	private String name;

	private int price;

	@Builder
	public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}
}
