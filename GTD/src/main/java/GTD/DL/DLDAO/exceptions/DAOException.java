/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.DL.DLDAO.exceptions;

/**
 * Trida reprezentuje vyjimky datové vrstvy (DL)
 *
 * @author slama
 */
public class DAOException extends RuntimeException {

    /**
     * Creates a new instance of <code>DAOException</code> without detail message.
     */
    public DAOException() {
    }

    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message.
     *
     * @param message the detail message.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs an instance of <code>DAOException</code> with the specified detail message and cause.
     *
     * @param message the detail message.
     * @param cause
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

}
