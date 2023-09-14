package simple.kiosk.spring.domain.stock;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.stock
 * fileName       : StockRepository
 * author         : Hyuk Kim
 * date           : 2023-09-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-14        Hyuk Kim       최초 생성
 */
public interface StockRepository extends JpaRepository<Stock, Long> {

	List<Stock> findAllByProductNumberIn(List<String> productNumbers);

}
