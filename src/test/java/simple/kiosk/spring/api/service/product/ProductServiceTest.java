package simple.kiosk.spring.api.service.product;

import static org.assertj.core.api.Assertions.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import simple.kiosk.spring.api.controller.product.request.ProductCreateRequest;
import simple.kiosk.spring.api.service.product.response.ProductResponse;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;

/**
 * packageName    : simple.kiosk.spring.api.service.product
 * fileName       : ProductServiceTest
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@SpringBootTest
@ActiveProfiles("test")
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@AfterEach
	void tearDown() {
		productRepository.deleteAllInBatch();
	}

	private Product createProduct(String productNumber, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.name(name)
			.price(price)
			.build();
	}

	private ProductCreateRequest createProductRequest() {
		return ProductCreateRequest.builder()
			.type(BOTTLE)
			.sellingStatus(SELLING)
			.name("카푸치노")
			.price(3000)
			.build();
	}

	@Test
	@DisplayName("신규 상품을 등록할 때 신규 상품이라면 상품 번호는 001이다.")
	void createProductWhenProductIsEmpty() {

		ProductCreateRequest createProduct = createProductRequest();

		ProductResponse productResponse = productService.createProduct(createProduct);

		assertThat(productResponse.getProductNumber()).isEqualTo("001");
	}

	@Test
	@DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품의 상품번호에서 1 증가한 값이다.")
	void createProduct() {
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 3000);
		Product product3 = createProduct("003", "우유", 2000);
		productRepository.saveAll(List.of(product1, product2, product3));

		ProductCreateRequest createProduct = createProductRequest();

		ProductResponse productResponse = productService.createProduct(createProduct);

		assertThat(productResponse)
			.extracting("productNumber", "name", "price")
			.contains("004", "카푸치노", 3000);
	}

}
