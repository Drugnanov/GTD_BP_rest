package GTD.restapi.util.exceptions;

/**
 * Created by Drugnanov on 11.12.2014.
 */
public class GtdGoogleTaskAlreadyReportedException extends Exception {

    public GtdGoogleTaskAlreadyReportedException() {
    }

    public GtdGoogleTaskAlreadyReportedException(String message) {
        super(message);
    }

    public GtdGoogleTaskAlreadyReportedException(String message, Throwable cause) {
        super(message, cause);
    }

    public GtdGoogleTaskAlreadyReportedException(Throwable cause) {
        super(cause);
    }
}
