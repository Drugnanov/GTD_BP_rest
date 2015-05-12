package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class GtdPublishInvalidTokenException extends Exception {

    public GtdPublishInvalidTokenException() {
    }

    public GtdPublishInvalidTokenException(String message) {
        super(message);
    }

    public GtdPublishInvalidTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public GtdPublishInvalidTokenException(Throwable cause) {
        super(cause);
    }
}
