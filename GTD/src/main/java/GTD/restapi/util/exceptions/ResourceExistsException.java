package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class ResourceExistsException extends Exception {

    public ResourceExistsException() {
    }

    public ResourceExistsException(String message) {
        super(message);
    }

    public ResourceExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceExistsException(Throwable cause) {
        super(cause);
    }
}
