package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class GtdApiException extends Exception {

    public GtdApiException() {
    }

    public GtdApiException(String message) {
        super(message);
    }

    public GtdApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public GtdApiException(Throwable cause) {
        super(cause);
    }
}
