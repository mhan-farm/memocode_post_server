package io.mhan.springjpatest2.comments.exception;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import io.mhan.springjpatest2.base.exception.GlobalException;
import lombok.Getter;

@Getter
public class CommentException extends GlobalException {

    private final ErrorCode errorCode;

    public CommentException(ErrorCode errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }
}
