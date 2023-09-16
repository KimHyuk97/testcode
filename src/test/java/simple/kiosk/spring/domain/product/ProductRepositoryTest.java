package simple.kiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.*;
import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : ProductRepositoryTest
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@DataJpaTest
@ActiveProfiles("test")
class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@BeforeEach
	void createProduct() {
		Product product = Product.builder()
			.productNumber("001")
			.type(HANDMADE)
			.sellingStatus(SELLING)
			.name("아메리카노")
			.price(4000)
			.build();

		Product product2 = Product.builder()
			.productNumber("002")
			.type(HANDMADE)
			.sellingStatus(HOLD)
			.name("카페라떼")
			.price(4500)
			.build();

		Product product3 = Product.builder()
			.productNumber("003")
			.type(HANDMADE)
			.sellingStatus(STOP_SELLING)
			.name("팥빙수")
			.price(7000)
			.build();

		productRepository.saveAll(List.of(product, product2, product3));
	}

	@AfterEach
	void clear() {
		productRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
	void findAllBySellingStatusIn() {
		// given
		List<ProductSellingStatus> selling = List.of(SELLING, HOLD);

		// when
		List<Product> products = productRepository.findAllBySellingStatusIn(selling);

		// then
		assertThat(products).hasSize(2)
			.extracting("productNumber", "name", "sellingStatus")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", SELLING),
				tuple("002", "카페라떼", HOLD)
			);
	}

	@Test
	@DisplayName("상품번호로 상품들을 조회한다.")
	void findAllByProductNumberIn() {
		// given
		List<String> productNumbers = List.of("001", "002");

		// when
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);

		// then
		assertThat(products).hasSize(2)
			.extracting("productNumber", "name", "sellingStatus")
			.containsExactlyInAnyOrder(
				tuple("001", "아메리카노", SELLING),
				tuple("002", "카페라떼", HOLD)
			);
	}

	@Test
	@DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
	void findLastedProduct() {
		// when
		String lastedProduct = productRepository.findLastedProductNumber();

		// then
		assertThat(lastedProduct).isEqualTo("003");
	}
}
