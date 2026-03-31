package hei.ingredient.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
    public BadRequestException(String field, Throwable reason) {
        super("Field " + field + " is invalid", reason);
    }
}
