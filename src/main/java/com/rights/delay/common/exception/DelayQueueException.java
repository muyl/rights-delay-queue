package com.rights.delay.common.exception;

/**
 * Delay queue exception
 *
 * @author 拓仲 on 2020/3/10
 */
public class DelayQueueException extends RuntimeException{
    private static final long serialVersionUID = 5018901344199973515L;

    /**
     * Delay queue exception
     *
     * @param errorMessage error message
     * @param args args
     */
    public DelayQueueException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    /**
     * Delay queue exception
     *
     * @param cause cause
     */
    public DelayQueueException(final Throwable cause) {
        super(cause);
    }
}
