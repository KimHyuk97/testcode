package simple.kiosk.spring.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.order
 * fileName       : OrderRepository
 * author         : Hyuk Kim
 * date           : 2023-09-12
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-12        Hyuk Kim       최초 생성
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
