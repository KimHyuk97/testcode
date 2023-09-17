package simple.kiosk.spring.api.controller.order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import simple.kiosk.spring.api.controller.order.response.OrderCreateRequest;
import simple.kiosk.spring.api.service.order.OrderService;

/**
 * packageName    : simple.kiosk.spring.api.controller.order
 * fileName       : OrderControllerTest
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private OrderService orderService;

	@Test
	@DisplayName("주문을 생성한다.")
	void createOrderTest() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of("001"))
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/orders/new")
					.content(mapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.message").value(HttpStatus.OK.name()))
		;
	}

	@Test
	@DisplayName("주문을 생성할 때 상품 번호 목록은 필수이다.")
	void createOrderWithoutProductNumbersTest() throws Exception {
		// given
		OrderCreateRequest request = OrderCreateRequest.builder()
			.productNumbers(List.of())
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/orders/new")
					.content(mapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.message").value("상품 번호 목록은 필수 입니다."))
		;
	}

}
