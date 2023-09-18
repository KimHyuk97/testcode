package simple.kiosk.spring.api.client.mail;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import simple.kiosk.spring.api.service.mail.MailService;
import simple.kiosk.spring.domain.history.mail.MailSendHistory;
import simple.kiosk.spring.domain.history.mail.MailSendHistoryRepository;

/**
 * packageName    : simple.kiosk.spring.api.client.mail
 * fileName       : MailSendClientTest
 * author         : Hyuk Kim
 * date           : 2023-09-19
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-19        Hyuk Kim       최초 생성
 */
@ExtendWith(MockitoExtension.class)
class MailServiceTest {

	@Mock
	private MailSendClient mailSendClient;

	@Mock
	private MailSendHistoryRepository mailSendHistoryRepository;

	@InjectMocks
	private MailService mailService;

	@DisplayName("메일 전송 테스트")
	@Test
	void sendMail() {
		// given
		//        Mockito.when(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
		//            .thenReturn(true);
		given(mailSendClient.sendEmail(anyString(), anyString(), anyString(), anyString()))
			.willReturn(true);

		// 		  Spy
		//        doReturn(true)
		//            .when(mailSendClient)
		//            .sendEmail(anyString(), anyString(), anyString(), anyString());

		// when
		boolean result = mailService.sendMail("", "", "", "");

		// then
		assertThat(result).isTrue();
		verify(mailSendHistoryRepository, times(1)).save(any(MailSendHistory.class));
	}

}
