package simple.kiosk.spring.api.controller.product.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

	@NotNull(message = "상품 타입은 필수 입니다.")
	private ProductType type;

	@NotNull(message = "상품 판매상태는 필수 입니다.")
	private ProductSellingStatus sellingStatus;

	@NotBlank(message = "상품명은 필수 입니다.")
	private String name;

	@Positive(message = "상품 가격은 0원 이상이여야 합니다.")
	private int price;

	@Builder
	public ProductCreateRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
		this.type = type;
		this.sellingStatus = sellingStatus;
		this.name = name;
		this.price = price;
	}
}
