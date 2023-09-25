package simple.kiosk.spring.domain.member;

import org.springframework.data.domain.Persistable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import simple.kiosk.spring.domain.BaseEntity;

/**
 * packageName    : simple.kiosk.spring.domain
 * fileName       : Member
 * author         : Hyuk Kim
 * date           : 2023-09-26
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-26        Hyuk Kim       최초 생성
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements Persistable<String> {

	@Id
	private String id;

	public Member(String id) {
		this.id = id;
	}

	@Override
	public boolean isNew() {
		return getCreateDateTime() == null;
	}
}
