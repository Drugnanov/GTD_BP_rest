package GTD.DL.DLDAO.exceptions;

/**
 * Created by Drugnanov on 4.1.2015.
 */
public class PermissionDeniedException extends DAOException {

    /**
     * Creates a new instance of <code>ItemNotFoundException</code> without detail message.
     */
    public PermissionDeniedException() {
    }

    /**
     * Constructs an instance of <code>ItemNotFoundException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public PermissionDeniedException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>ItemNotFoundException</code> with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause
     */
    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
}
