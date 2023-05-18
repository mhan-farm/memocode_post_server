package io.mhan.springjpatest2.base.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    COMMENT_NOT_FOUND(404, "comment가 존재하지 않습니다."),
    COMMENT_NOT_MODIFY(403, "comment를 수정할 권한이 없습니다."),
    COMMENT_NOT_DELETE(403, "comment를 삭제할 권한이 없습니다."),

    USER_NOT_FOUND(404, "user가 존재하지 않습니다."),

    POST_NOT_FOUND(404, "post가 존재하지 않습니다."),
    POST_NOT_MODIFY(403, "post를 수정할 권한이 없습니다."),
    POST_NOT_DELETE(403, "post를 삭제할 권한이 없습니다."),
    ;

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
