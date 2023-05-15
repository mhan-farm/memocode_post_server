package io.mhan.springjpatest2.base.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> {

    private int status;

    private String message;

    private T data;

    public static <T> SuccessResponse<T> ok(String message, T data) {
        return new SuccessResponse<>(200, message, data);
    }
}
