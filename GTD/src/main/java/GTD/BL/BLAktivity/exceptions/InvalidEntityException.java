/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GTD.BL.BLAktivity.exceptions;

/**
 * InvalidEntityException is thrown when entity's data are in some way invalid (max length exceeded, ...)
 *
 * @author slama
 */
public class InvalidEntityException extends RuntimeException {

    /**
     * Creates a new instance of <code>InvalidEntityException</code> without detail message.
     */
    public InvalidEntityException() {
    }

    /**
     * Constructs an instance of <code>InvalidEntityException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidEntityException(String msg) {
        super(msg);
    }
}
