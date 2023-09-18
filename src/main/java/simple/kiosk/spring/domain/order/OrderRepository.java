package simple.kiosk.spring.domain.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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

	@Query("select o from Order o where o.registeredDateTime >= :startDateTime"
		+ " and o.registeredDateTime < :endDateTime"
		+ " and o.orderStatus = :orderStatus")
	List<Order> findOrdersBy(LocalDateTime startDateTime, LocalDateTime endDateTime, OrderStatus orderStatus);
}
