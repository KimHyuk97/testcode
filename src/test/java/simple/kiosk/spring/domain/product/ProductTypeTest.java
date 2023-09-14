package simple.kiosk.spring.domain.product;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : ProductTypeTest
 * author         : Hyuk Kim
 * date           : 2023-09-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-14        Hyuk Kim       최초 생성
 */
class ProductTypeTest {

	@Test
	@DisplayName("재고관리의 해야할 상품 타입이 포함되는지 확인하다.")
	void containsStockType() {

		ProductType bakery = ProductType.BAKERY;

		boolean result = ProductType.containsStockType(bakery);

		Assertions.assertThat(result).isTrue();
	}
}
