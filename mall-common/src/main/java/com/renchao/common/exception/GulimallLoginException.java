package com.renchao.common.exception;

public class GulimallLoginException extends RuntimeException {
    public GulimallLoginException() {
        super();
    }

    public GulimallLoginException(String message) {
        super(message);
    }

    public GulimallLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public GulimallLoginException(Throwable cause) {
        super(cause);
    }

    protected GulimallLoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
