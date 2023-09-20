package com.renchao.common.exception;

public class SubmitOrderException extends RuntimeException{
    public SubmitOrderException() {
        super();
    }

    public SubmitOrderException(String message) {
        super(message);
    }

    public SubmitOrderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubmitOrderException(Throwable cause) {
        super(cause);
    }

    protected SubmitOrderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
