package simple.kiosk.spring.api;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * packageName    : simple.kiosk.spring.api
 * fileName       : Response
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@Getter
public class Response<T> {
	private int code;
	private HttpStatus status;
	private String message;
	private T data;

	public Response(HttpStatus status, String message, T data) {
		this.code = status.value();
		this.status = status;
		this.message = message;
		this.data = data;
	}

	public static <T> Response<T> of(HttpStatus status, String massage, T data) {
		return new Response<>(status, massage, data);
	}

	public static <T> Response<T> ok(T data) {
		return of(HttpStatus.OK, HttpStatus.OK.name(), data);
	}

	public static <T> Response<T> ok(String message, T data) {
		return of(HttpStatus.OK, message, data);
	}
}
