package core.model.exception;

public class NoSuchUserException extends Exception {
    private NoSuchUserException(String message) {
        super(message);
    }
}
