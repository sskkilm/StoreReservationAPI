package zerobase.storereservationapi.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    INVALID_TOKEN_SIGNATURE("잘못된 JWT 서명입니다."),
    EXPIRED_TOKEN("만료된 JWT 토큰입니다."),
    NOT_SUPPORTED_TOKEN("지원되지 않는 JWT 토큰 입니다."),
    INVALID_TOKEN("JWT 토큰이 잘못되었습니다."),

    INTERNAL_SERVER_ERROR("내부 서버 오류가 발생했습니다."),
    INVALID_REQUEST("잘못된 요청입니다."),

    AlREADY_EXIST_STORE("이미 존재하는 매장입니다."),
    STORE_NOT_FOUND("존재하지 않는 매장입니다."),

    USER_NOT_FOUND("존재하지 않는 유저입니다."),
    AlREADY_EXIST_USER("이미 존재하는 유저입니다."),
    PASSWORD_UN_MATCH("비밀번호가 일치하지 않습니다."),


    ALREADY_RESERVED("이미 예약된 시간입니다."),
    RESERVATION_NOT_FOUND("존재하지 않는 예약입니다."),
    RESERVATION_ALREADY_PROCESSED("이미 처리된 예약입니다."),
    RESERVATION_NOT_APPROVED("승인된 예약이 아닙니다."),
    RESERVATION_DATE_UN_MATCH("예약 날짜가 일치하지 않습니다."),
    ARRIVAL_TIME_HAS_PASSED("도착 시간이 지났습니다."),

    AlREADY_EXIST_REVIEW("이미 리뷰가 존재합니다."),
    NO_REVIEW_PERMISSION("리뷰 권한이 없습니다."),
    REVIEW_NOT_FOUND("존재하지 않는 리뷰입니다.")
    ;

    private final String message;
}
