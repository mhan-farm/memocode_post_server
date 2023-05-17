package io.mhan.springjpatest2.base.exception;

import lombok.Getter;

@Getter
public abstract class GlobalException extends RuntimeException {

    private ErrorCode errorCode;

    public GlobalException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
