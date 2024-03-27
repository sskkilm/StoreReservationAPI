package zerobase.storereservationapi.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.type.ReservationType;

import java.time.LocalDate;
import java.time.LocalTime;

public class CreateReservation {
    @Getter
    public static class Request {
        @NotNull
        private Long storeId;

        @NotNull
        @FutureOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate date;

        @NotNull
        @FutureOrPresent
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime time;

        @NotNull
        @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
        private String phoneNumber;
    }

    @Getter
    @Builder
    public static class Response {
        private ReservationType reservationType;
        private String storeName;
        private LocalDate date;
        private LocalTime time;
        private String phoneNumber;
        private String reservationId;

        public static Response toDto(Reservation reservation) {
            return Response.builder()
                    .reservationType(reservation.getReservationType())
                    .storeName(reservation.getStore().getName())
                    .date(reservation.getDate())
                    .time(reservation.getTime())
                    .phoneNumber(reservation.getPhoneNumber())
                    .reservationId(reservation.getReservationId())
                    .build();
        }
    }
}
