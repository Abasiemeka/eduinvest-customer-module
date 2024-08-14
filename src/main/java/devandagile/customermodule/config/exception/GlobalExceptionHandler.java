package devandagile.customermodule.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import devandagile.customermodule.config.exception.Exceptions.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGenericException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("An internal server error occurred: " + ex.getMessage());
	}

	@ExceptionHandler(EmailSendingException.class)
	public ResponseEntity<String> handleEmailSendingException(EmailSendingException ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body("Failed to send the email: " + ex.getMessage() + " " + ex.getCause() + " ALSO " + Arrays.toString(ex.getStackTrace()));
	}

	// Add other specific exception handlers as needed
}
