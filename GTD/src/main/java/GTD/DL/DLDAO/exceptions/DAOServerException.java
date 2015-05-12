/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLDAO.exceptions;


/**
 * Exception thrown when there is an error in application logic resulting in 5** response (i.e. when it's server's
 * fault, not client's)
 *
 * @author slama
 */
public class DAOServerException extends DAOException {

    /**
     * Creates a new instance of <code>ConstraintException</code> without detail message.
     */
    public DAOServerException() {
    }

    /**
     * Constructs an instance of <code>ConstraintException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DAOServerException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>ConstraintException</code> with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause
     */
    public DAOServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
