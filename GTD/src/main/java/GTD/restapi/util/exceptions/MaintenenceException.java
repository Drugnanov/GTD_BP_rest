package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class MaintenenceException extends Exception {

    public MaintenenceException() {
    }

    public MaintenenceException(String message) {
        super(message);
    }

    public MaintenenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public MaintenenceException(Throwable cause) {
        super(cause);
    }
}
