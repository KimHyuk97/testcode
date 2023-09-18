package simple.kiosk.spring.api.client.mail;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : simple.kiosk.spring.api.client.mail
 * fileName       : MailSendClient
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
@Slf4j
@Component
public class MailSendClient {

	public boolean sendEmail(String fromEmail, String toEmail, String subject, String content) {
		log.info("메일 전송");
		throw new IllegalArgumentException("메일 전송");
	}

}
