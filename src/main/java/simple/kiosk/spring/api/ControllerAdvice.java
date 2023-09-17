package simple.kiosk.spring.api;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : simple.kiosk.spring.api
 * fileName       : ControllerAdvice
 * author         : Hyuk Kim
 * date           : 2023-09-17
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-09-17        Hyuk Kim       최초 생성
 */
@RestControllerAdvice
public class ControllerAdvice {

	@ExceptionHandler(BindException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Response<Object> bingException(BindException e) {

		String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		return Response.of(HttpStatus.BAD_REQUEST, errorMessage, null);
	}
}
