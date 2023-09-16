package simple.kiosk.spring.api.service.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.controller.product.request.ProductCreateRequest;
import simple.kiosk.spring.api.service.product.response.ProductResponse;
import simple.kiosk.spring.domain.product.Product;
import simple.kiosk.spring.domain.product.ProductRepository;
import simple.kiosk.spring.domain.product.ProductSellingStatus;

/**
 * packageName    : simple.kiosk.spring.api.service.product
 * fileName       : ProductService
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

	private final ProductRepository productRepository;

	public List<ProductResponse> getSellingProducts() {

		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.toList();
	}

	// 동시성 이슈
	@Transactional
	public ProductResponse createProduct(ProductCreateRequest request) {

		String newProductNumber = createProductNumber();

		Product product = Product.createProduct(newProductNumber, request);

		Product savedProduct = productRepository.save(product);

		return ProductResponse.of(savedProduct);
	}

	private String createProductNumber() {

		String lastedProductNumber = productRepository.findLastedProductNumber();

		if (lastedProductNumber == null) {
			return "001";
		}

		int currentProductNumber = Integer.parseInt(lastedProductNumber);
		int nextProductNumber = currentProductNumber + 1;

		return String.format("%03d", nextProductNumber);
	}

}
