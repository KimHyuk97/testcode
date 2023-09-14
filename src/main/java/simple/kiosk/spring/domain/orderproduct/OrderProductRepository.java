package simple.kiosk.spring.domain.orderproduct;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.orderproduct
 * fileName       : OrderProductRepository
 * author         : Hyuk Kim
 * date           : 2023-09-14
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-14        Hyuk Kim       최초 생성
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
}
