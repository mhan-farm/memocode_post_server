package io.mhan.springjpatest2.base.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
public class SuccessResponse<T> {

    private int status;

    private String message;

    private T data;

    public static <T> SuccessResponse<T> ok(String message, T data) {
        return new SuccessResponse<>(200, message, data);
    }

    public static <T> SuccessResponse<T> create(String message, T data) {
        return new SuccessResponse<>(201, message, data);
    }

    public static SuccessResponse<Void> noContent(String message) {
        return new SuccessResponse<>(204, message, null);
    }
}
