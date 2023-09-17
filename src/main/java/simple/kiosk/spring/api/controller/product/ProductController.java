package simple.kiosk.spring.api.controller.product;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.Response;
import simple.kiosk.spring.api.controller.product.request.ProductCreateRequest;
import simple.kiosk.spring.api.service.product.ProductService;
import simple.kiosk.spring.api.service.product.response.ProductResponse;

/**
 * packageName    : simple.kiosk.spring.api.controller.product
 * fileName       : ProductController
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
@RestController
@RequiredArgsConstructor
public class ProductController {

	private final ProductService productService;

	@PostMapping("/api/v1/products/new")
	public Response<ProductResponse> createProduct(@RequestBody @Valid ProductCreateRequest request) {
		return Response.ok(productService.createProduct(request));
	}

	@GetMapping("/api/v1/products/selling")
	public Response<List<ProductResponse>> getSellingProducts() {
		return Response.ok(productService.getSellingProducts());
	}
}
