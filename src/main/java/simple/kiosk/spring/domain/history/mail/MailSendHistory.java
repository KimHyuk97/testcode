package simple.kiosk.spring.domain.history.mail;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.domain.BaseEntity;

/**
 * packageName    : simple.kiosk.spring.domain.history.mail
 * fileName       : MailSendHistory
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class MailSendHistory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fromEmail;
	private String toEmail;
	private String subject;
	private String content;

	@Builder
	private MailSendHistory(String fromEmail, String toEmail, String subject, String content) {
		this.fromEmail = fromEmail;
		this.toEmail = toEmail;
		this.subject = subject;
		this.content = content;
	}

}
