package simple.kiosk.spring.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * packageName    : simple.kiosk.spring.domain.member
 * fileName       : MemberRepository
 * author         : Hyuk Kim
 * date           : 2023-09-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-26        Hyuk Kim       최초 생성
 */
public interface MemberRepository extends JpaRepository<Member, String> {

}
