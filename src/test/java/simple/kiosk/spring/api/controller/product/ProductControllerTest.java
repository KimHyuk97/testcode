package simple.kiosk.spring.api.controller.product;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import simple.kiosk.spring.api.controller.product.request.ProductCreateRequest;
import simple.kiosk.spring.api.service.product.ProductService;
import simple.kiosk.spring.api.service.product.response.ProductResponse;

/**
 * packageName    : simple.kiosk.spring.api.controller.product
 * fileName       : ProductControllerTest
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@ActiveProfiles("test")
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private ProductService productService;

	@Test
	@DisplayName("상품을 등록한다.")
	void createProductTest() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(3000)
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	@DisplayName("상품을 등록할 때 필수 값이 매핑되어있는지 확인한다.")
	void createProductWithoutTypeTest() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(3000)
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.message").value("상품 타입은 필수 입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
		;
	}

	@Test
	@DisplayName("상품을 등록할 때 필수 값이 매핑되어있는지 확인한다.")
	void createProductWithoutSellingStatusTest() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(BOTTLE)
			.name("카푸치노")
			.price(3000)
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.message").value("상품 판매상태는 필수 입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
		;
	}

	@Test
	@DisplayName("상품을 등록할 때 필수 값이 매핑되어있는지 확인한다.")
	void createProductWithoutNameTest() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.price(3000)
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.message").value("상품명은 필수 입니다."))
			.andExpect(jsonPath("$.data").isEmpty())
		;
	}

	@Test
	@DisplayName("상품을 등록할 때 필수 값이 매핑되어있는지 확인한다.")
	void createProductWithoutPriceTest() throws Exception {
		// given
		ProductCreateRequest request = ProductCreateRequest.builder()
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(0)
			.build();

		// when + then
		mockMvc.perform(
				post("/api/v1/products/new")
					.content(objectMapper.writeValueAsString(request))
					.contentType(MediaType.APPLICATION_JSON)
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value(HttpStatus.BAD_REQUEST.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.name()))
			.andExpect(jsonPath("$.message").value("상품 가격은 0원 이상이여야 합니다."))
			.andExpect(jsonPath("$.data").isEmpty())
		;
	}

	@Test
	@DisplayName("판매상태 상품 목록을 조회한다.")
	void getProductsTest() throws Exception {
		// given
		List<ProductResponse> result = List.of();

		when(productService.getSellingProducts()).thenReturn(result);

		// when + then
		mockMvc.perform(
				get("/api/v1/products/selling")
			)
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value(HttpStatus.OK.value()))
			.andExpect(jsonPath("$.status").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.message").value(HttpStatus.OK.name()))
			.andExpect(jsonPath("$.data").isArray())
		;
	}

}
