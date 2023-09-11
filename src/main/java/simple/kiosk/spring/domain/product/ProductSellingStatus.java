package simple.kiosk.spring.domain.product;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : ProductSellingStatus
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
public enum ProductSellingStatus {

	SELLING("판매중"),
	HOLD("판매보류"),
	STOP_SELLING("판매중지");

	private final String value;

	public static List<ProductSellingStatus> forDisplay() {
		return List.of(SELLING, HOLD);
	}
}
