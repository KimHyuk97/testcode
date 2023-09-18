package simple.kiosk.spring.api.service.mail;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import simple.kiosk.spring.api.client.mail.MailSendClient;
import simple.kiosk.spring.domain.history.mail.MailSendHistory;
import simple.kiosk.spring.domain.history.mail.MailSendHistoryRepository;

/**
 * packageName    : simple.kiosk.spring.api.service.mail
 * fileName       : MailService
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
@Service
@RequiredArgsConstructor
public class MailService {

	private final MailSendClient mailSendClient;
	private final MailSendHistoryRepository mailSendHistoryRepository;

	public boolean sendMail(String fromEmail, String toEmail, String subject, String content) {
		boolean result = mailSendClient.sendEmail(fromEmail, toEmail, subject, content);
		if (result) {
			mailSendHistoryRepository.save(MailSendHistory.builder()
				.fromEmail(fromEmail)
				.toEmail(toEmail)
				.subject(subject)
				.content(content)
				.build()
			);
			return true;
		}

		return false;
	}

}
