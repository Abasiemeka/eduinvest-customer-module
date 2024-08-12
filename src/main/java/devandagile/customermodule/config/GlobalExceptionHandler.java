package devandagile.customermodule.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Configuration
@RestControllerAdvice
public class GlobalExceptionHandler{

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleAllExceptions(Exception e) {
		return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.EXPECTATION_FAILED);
	}
}
