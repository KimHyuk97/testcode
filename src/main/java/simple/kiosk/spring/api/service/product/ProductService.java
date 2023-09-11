package simple.kiosk.spring.api.service.product;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
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
public class ProductService {

	private final ProductRepository productRepository;


	public List<ProductResponse> getSellingProducts() {

		List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

		return products.stream()
			.map(ProductResponse::of)
			.toList();
	}


}
