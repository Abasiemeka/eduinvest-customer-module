package devandagile.customermodule.model.dto;

import lombok.Builder;
import java.util.Optional;

@Builder
public record GenericResponse(
    String message,
    ResponseStatus status,
    Optional<Object> data
) {
    public enum ResponseStatus {
        SUCCESS, ERROR
    }

    public static GenericResponse successful(String message) {
        return successful(message, null);
    }

    public static GenericResponse successful(String message, Object data) {
        validateMessage(message);
        return GenericResponse.builder()
                .message(message)
                .status(ResponseStatus.SUCCESS)
                .data(Optional.ofNullable(data))
                .build();
    }

    public static GenericResponse error(String message) {
        return error(message, null);
    }

    public static GenericResponse error(String message, Object data) {
        validateMessage(message);
        return GenericResponse.builder()
                .message(message)
                .status(ResponseStatus.ERROR)
                .data(Optional.ofNullable(data))
                .build();
    }

    public static GenericResponse successful() {
        return successful("Operation successful");
    }

    public static GenericResponse error() {
        return error("An error occurred");
    }

    private static void validateMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message cannot be null or empty");
        }
    }

    @Override
    public String toString() {
        return "GenericResponse{" +
                "message='" + message + '\'' +
                ", status=" + status +
                ", data=" + (data.isPresent() ? data.get() : "null") +
                '}';
    }
}
