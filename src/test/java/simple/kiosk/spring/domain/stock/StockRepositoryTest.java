package simple.kiosk.spring.domain.stock;

import static simple.kiosk.spring.domain.product.ProductSellingStatus.*;
import static simple.kiosk.spring.domain.product.ProductType.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;

/**
 * packageName    : simple.kiosk.spring.domain.stock
 * fileName       : StockRepositoryTest
 * author         : Hyuk Kim
 * date           : 2023-09-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-14        Hyuk Kim       최초 생성
 */
@DataJpaTest
@ActiveProfiles("test")
class StockRepositoryTest {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private StockRepository stockRepository;

	private static Product createProduct(String productNumber, String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(HANDMADE)
			.sellingStatus(SELLING)
			.name(name)
			.price(price)
			.build();
	}

	@Test
	@DisplayName("재고관리가 필요한 상품 번호들을 조회한다.")
	void findAllByProductNumbersIn() {
		Product product1 = createProduct("001", "아메리카노", 2000);
		Product product2 = createProduct("002", "카페라떼", 2500);
		Product product3 = createProduct("003", "빙수", 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		Stock americanoStock = Stock.create("001", 2);
		Stock letteStock = Stock.create("002", 2);
		stockRepository.saveAll(List.of(americanoStock, letteStock));

		List<Stock> stocks = stockRepository.findAllByProductNumberIn(List.of("001", "002"));

		Assertions.assertThat(stocks).hasSize(2)
			.extracting("productNumber", "quantity")
			.containsExactlyInAnyOrder(
				Tuple.tuple("001", 2),
				Tuple.tuple("002", 2)
			);
	}
}
