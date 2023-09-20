package io.mhan.springjpatest2.base.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    COMMENT_NOT_FOUND(404, "comment가 존재하지 않습니다."),
    COMMENT_NOT_MODIFY(403, "comment를 수정할 권한이 없습니다."),
    COMMENT_NOT_DELETE(403, "comment를 삭제할 권한이 없습니다."),

    AUTHOR_NOT_FOUND(404, "author가 존재하지 않습니다."),

    POST_NOT_FOUND(404, "post가 존재하지 않습니다."),
    POST_NOT_MODIFY(403, "post를 수정할 권한이 없습니다."),
    POST_NOT_DELETE(403, "post를 삭제할 권한이 없습니다."),
    CAN_NOT_ACCESS_POST(403, "post에 접근할 권한이 없습니다."),
    INCORRECT_SEQUENCE_NUMBER_POST(404, "잘못된 번호입니다."),
    ALREADY_EQUAL_SEQUENCE_POST(404, "이미 같은 순서입니다."),

    INTERNAL_ERROR(500, "Internal Error"),
    ;

    private final int status;

    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
