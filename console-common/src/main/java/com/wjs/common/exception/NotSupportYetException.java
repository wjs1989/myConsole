package com.wjs.common.exception;

/**
 * @author wenjs
 */
public class NotSupportYetException  extends RuntimeException {
    public NotSupportYetException() {
        super();
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param message the message
     */
    public NotSupportYetException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public NotSupportYetException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Should never happen exception.
     *
     * @param cause the cause
     */
    public NotSupportYetException(Throwable cause) {
        super(cause);
    }
}
