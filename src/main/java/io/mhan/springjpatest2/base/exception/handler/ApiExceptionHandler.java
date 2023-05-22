package io.mhan.springjpatest2.base.exception.handler;

import io.mhan.springjpatest2.base.exception.ErrorCode;
import io.mhan.springjpatest2.base.exception.GlobalException;
import io.mhan.springjpatest2.base.response.ErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = GlobalException.class)
    public ErrorResponse userExceptionHandler(GlobalException ex, HttpServletResponse response) {
        ErrorCode errorCode = ex.getErrorCode();

        response.setStatus(errorCode.getStatus());
        return ErrorResponse.of(errorCode.getStatus(), errorCode.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse exception(Exception ex) {
        log.info("internal error : {}", ex.getMessage());
        return ErrorResponse.internalError();
    }
}
