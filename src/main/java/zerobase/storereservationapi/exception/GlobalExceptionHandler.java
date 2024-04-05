package zerobase.storereservationapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import zerobase.storereservationapi.dto.ErrorResponse;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ErrorResponse handleCustomException(CustomException e) {
        return new ErrorResponse(e.getErrorCode(), e.getErrorMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return new ErrorResponse(INVALID_REQUEST, INVALID_REQUEST.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ErrorResponse handleAccessDeniedException(AccessDeniedException e) {
        return new ErrorResponse(ACCESS_DENIED, ACCESS_DENIED.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse handleException(Exception e) {
        log.info(e + "->" + e.getMessage());
        return new ErrorResponse(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR.getMessage());
    }

}
