package com.innovation.arduino.exception;

/**
 * Description:
 *
 * @author xxz
 * Created on 03/20/2018
 * <p>
 * Update:
 * @author xxz
 * Last modified on
 */
public class NotFoundException extends RuntimeException {
    /**
     * Constructs a new com.innovation.arduino.exception with the specified detail message.  The
     * cause is not initialized, and may subsequently be initialized by
     * a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public NotFoundException(String message) {
        super(message);
    }
}
