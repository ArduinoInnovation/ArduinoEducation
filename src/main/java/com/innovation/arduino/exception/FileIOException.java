package com.innovation.arduino.exception;

/**
 * Description:
 * 文件IO的异常，之所以不用IOException是为了用 controllerAdvice来捕获异常
 *
 * @author xxz
 * Created on 03/22/2018
 */
public class FileIOException extends RuntimeException {
    /**
     * Constructs a new runtime com.innovation.arduino.exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public FileIOException(String message) {
        super(message);
    }
}
