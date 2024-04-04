package zerobase.storereservationapi.exception;

import lombok.Getter;
import zerobase.storereservationapi.type.ErrorCode;

@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
    private String errorMessage;

    public CustomException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getMessage();
    }
}
