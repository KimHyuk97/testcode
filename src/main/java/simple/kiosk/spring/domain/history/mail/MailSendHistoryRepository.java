package simple.kiosk.spring.domain.history.mail;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.history.mail
 * fileName       : MailSendHistoryRepository
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
public interface MailSendHistoryRepository extends JpaRepository<MailSendHistory, Long> {
}
