package com.renchao.common.exception;

public class MallLoginException extends RuntimeException {
    public MallLoginException() {
        super();
    }

    public MallLoginException(String message) {
        super(message);
    }

    public MallLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public MallLoginException(Throwable cause) {
        super(cause);
    }

    protected MallLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
