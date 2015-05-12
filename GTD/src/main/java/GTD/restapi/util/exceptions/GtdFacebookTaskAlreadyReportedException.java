package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class GtdFacebookTaskAlreadyReportedException extends Exception {

    public GtdFacebookTaskAlreadyReportedException() {
    }

    public GtdFacebookTaskAlreadyReportedException(String message) {
        super(message);
    }

    public GtdFacebookTaskAlreadyReportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GtdFacebookTaskAlreadyReportedException(Throwable cause) {
        super(cause);
    }
}
