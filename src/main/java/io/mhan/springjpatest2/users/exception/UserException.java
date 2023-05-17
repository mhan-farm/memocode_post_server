package io.mhan.springjpatest2.users.exception;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import io.mhan.springjpatest2.base.exception.GlobalException;
import lombok.Getter;


@Getter
public class UserException extends GlobalException {

    private final ErrorCode errorCode;

    public UserException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
