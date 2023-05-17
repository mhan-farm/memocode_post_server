package io.mhan.springjpatest2.base.exception;

import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {

    private ErrorCode errorCode;

    public GlobalException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
