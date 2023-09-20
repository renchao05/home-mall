package com.renchao.common.exception;

/**
 * 库存锁定异常
 */
public class WareLockException extends RuntimeException {
    public WareLockException() {
        super();
    }

    public WareLockException(String message) {
        super(message);
    }

    public WareLockException(String message, Throwable cause) {
        super(message, cause);
    }

    public WareLockException(Throwable cause) {
        super(cause);
    }

    protected WareLockException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
