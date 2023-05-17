package io.mhan.springjpatest2.users.exception;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
