package io.mhan.springjpatest2.base.exception.handler;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import io.mhan.springjpatest2.base.exception.GlobalException;
import io.mhan.springjpatest2.base.response.ErrorResponse;
import io.mhan.springjpatest2.posts.controller.AjaxPostController;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackageClasses = {AjaxPostController.class})
public class AjaxExceptionHandler {

    @ExceptionHandler(value = GlobalException.class)
    public ErrorResponse userExceptionHandler(GlobalException ex, HttpServletResponse response) {
        ErrorCode errorCode = ex.getErrorCode();

        response.setStatus(errorCode.getStatus());
        return ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(GlobalException ex, HttpServletResponse response) {
        return ErrorResponse.internalError();
    }
}
