package io.mhan.springjpatest2.posts.exception;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import io.mhan.springjpatest2.base.exception.GlobalException;
import lombok.Getter;

@Getter
public class PostException extends GlobalException {

    private final ErrorCode errorCode;

    public PostException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
