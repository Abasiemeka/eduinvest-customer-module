package devandagile.customermodule.config.exception;

import java.io.IOException;

public class Exceptions extends Exception{
	/**
	 * Exception thrown when email sending attempt fails.
	 * This exception indicates that the email was not successfully sent.
	 */
	public static class EmailSendingException extends RuntimeException {
		public EmailSendingException(String message) {
			super(message);
		}

		public EmailSendingException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	/**
	 * Exception thrown when a user validation attempt fails.
	 * This exception indicates that the user information provided does not match the information in the DB.
	 */
	public static class UserValidationException extends RuntimeException{
		public UserValidationException(String s){ super("User details provided are not valid.");}
	}

	/**
	 * Exception thrown when a user attempts to access a feature they are not authorized for in MockBook.
	 * This exception indicates that the user does not have the necessary permissions to perform the requested action.
	 */
	public static class UnauthorizedAccessException extends RuntimeException {

		/**
		 * Constructs a new UnauthorizedAccessException with a default error message.
		 */
		public UnauthorizedAccessException(String s) {
			super("User is not authorized to access this feature.");
		}
	}

	/**
	 * Exception thrown when a requested User is not found or does not exist in MockBook.
	 * This exception indicates that the requested User does not exist in the scope of the specified fetch location.
	 * It can also mean that the user does not exist at all, in the application.
	 */
	public static class UserNotFoundException extends RuntimeException{

		/**
		 * Constructs a new UserNotFoundException with a default error message.
		 */
		public UserNotFoundException(String s) {
			super("The requested user was not found or does not exist.");
		}
	}

	/**
	 * Exception thrown when a requested User is not found or does not exist in MockBook.
	 * This exception indicates that the requested User does not exist in the scope of the specified fetch location.
	 * It can also mean that the user does not exist at all, in the application.
	 */
	public static class UserAlreadyExistsException extends RuntimeException{

		/**
		 * Constructs a new UserNotFoundException with a default error message.
		 */
		public UserAlreadyExistsException(String s) {
			super("This user already exists: " + s);
		}
	}

	/**
	 * Exception thrown when the requested profile picture resource is not found or does not exist in the database.
	 * This exception indicates that the requested User does not exist in the scope of the specified fetch location.
	 */
	public static class ProfilePicException extends IOException {

		/**
		 * Constructs a new ProfilePicException with a default error message.
		 */
		public ProfilePicException(String s) {
			super("Profile Pic could not be saved: " + s);
		}
	}

	/**
	 * Exception thrown when the requested profile picture resource is not found or does not exist in the database.
	 * This exception indicates that the requested User does not exist in the scope of the specified fetch location.
	 */
	public static class AuthenticationException extends RuntimeException {
		public AuthenticationException(String s) { super("User could not be authenticated.");}
	}

	/**
	 * Exception thrown when the requested resource is not found or does not exist.
	 * This exception indicates that the requested resource does not exist in the scope of the specified fetch location.
	 */
	public static class ResourceNotFoundException extends RuntimeException {
		public ResourceNotFoundException(String s) { super("The requested resource cannot be found.");}
	}

	public static class UnauthorizedActionException extends RuntimeException {
		public UnauthorizedActionException(String s) { super("Action not authorized!");
		}
	}
}
