/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLDAO.exceptions;


/**
 * Exception thrown when database constraint is (about to be) violated
 *
 * @author slama
 */
public class ConstraintException extends DAOException {

    /**
     * Creates a new instance of <code>ConstraintException</code> without detail message.
     */
    public ConstraintException() {
    }

    /**
     * Constructs an instance of <code>ConstraintException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public ConstraintException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>ConstraintException</code> with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause
     */
    public ConstraintException(String message, Throwable cause) {
        super(message, cause);
    }
}
