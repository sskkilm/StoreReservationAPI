package zerobase.storereservationapi.dto;

import lombok.Getter;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitCheck {
    @Getter
    public static class Request {
        @NonNull
        private String reservationId;
        @NonNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate arrivalDate;
        @NonNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
        private LocalTime arrivalTime;
    }
}
