package io.mhan.springjpatest2.posts.exception;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

    private final ErrorCode errorCode;

    public PostException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
