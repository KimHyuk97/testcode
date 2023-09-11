package simple.kiosk.spring.domain.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.product
 * fileName       : ProductRepository
 * author         : Hyuk Kim
 * date           : 2023-09-11
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-11        Hyuk Kim       최초 생성
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatuses);

	List<Product> findAllByProductNumberIn(List<String> productNumbers);
}
