package com.yxqy.exception;

/**
 * @author 拓仲 on 2020/3/10
 */
public class DelayQueueException extends RuntimeException{
    private static final long serialVersionUID = 5018901344199973515L;

    public DelayQueueException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public DelayQueueException(final Throwable cause) {
        super(cause);
    }
}
