package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class BadLoginException extends Exception {

    public BadLoginException() {
    }

    public BadLoginException(String message) {
        super(message);
    }

    public BadLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public BadLoginException(Throwable cause) {
        super(cause);
    }
}
