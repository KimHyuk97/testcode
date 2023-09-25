package simple.kiosk.spring.domain.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * packageName    : simple.kiosk.spring.domain.member
 * fileName       : MemberRepositoryTest
 * author         : Hyuk Kim
 * date           : 2023-09-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-26        Hyuk Kim       최초 생성
 */
@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	@Test
	public void save() {
		Member member = new Member("A");
		memberRepository.save(member);
	}

}
