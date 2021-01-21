package core.model.exception;

public class AuthenticationException extends Exception {
    private AuthenticationException(String message) {
        super(message);
    }
}
