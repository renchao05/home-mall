package com.renchao.common.exception;

public class MallRegisterException extends RuntimeException{
    public MallRegisterException() {
        super();
    }

    public MallRegisterException(String message) {
        super(message);
    }

    public MallRegisterException(String message, Throwable cause) {
        super(message, cause);
    }

    public MallRegisterException(Throwable cause) {
        super(cause);
    }

    protected MallRegisterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
