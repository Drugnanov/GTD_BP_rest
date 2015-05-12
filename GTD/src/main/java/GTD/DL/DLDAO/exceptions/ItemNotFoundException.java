/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLDAO.exceptions;

/**
 * Exception thrown when item is not in database (and is expected to be)
 *
 * @author slama
 */
public class ItemNotFoundException extends DAOException {

    /**
     * Creates a new instance of <code>ItemNotFoundException</code> without detail message.
     */
    public ItemNotFoundException() {
    }

    /**
     * Constructs an instance of <code>ItemNotFoundException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>ItemNotFoundException</code> with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause
     */
    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
