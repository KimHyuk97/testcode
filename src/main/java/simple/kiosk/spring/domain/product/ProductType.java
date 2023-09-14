package simple.kiosk.spring.domain.product;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : ProductType
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */

@Getter
@RequiredArgsConstructor
public enum ProductType {

	HANDMADE("제조 음료"),
	BOTTLE("병 음료"),
	BAKERY("베이커리");

	private final String value;

	public static boolean containsStockType(ProductType type) {
		return List.of(BAKERY, BOTTLE).contains(type);
	}
}
