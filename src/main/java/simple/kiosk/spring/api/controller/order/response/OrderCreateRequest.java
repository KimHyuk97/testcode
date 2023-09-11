package simple.kiosk.spring.api.controller.order.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

/**
 * packageName    : simple.kiosk.spring.api.controller.order.response
 * fileName       : OrderCreateRequest
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Getter
@Builder
public class OrderCreateRequest {

	private List<String> productNumbers = new ArrayList<>();
}
