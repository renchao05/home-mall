package com.renchao.common.exception;

public class GulimallRegisterException extends RuntimeException{
    public GulimallRegisterException() {
        super();
    }

    public GulimallRegisterException(String message) {
        super(message);
    }

    public GulimallRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public GulimallRegisterException(Throwable cause) {
        super(cause);
    }

    protected GulimallRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
