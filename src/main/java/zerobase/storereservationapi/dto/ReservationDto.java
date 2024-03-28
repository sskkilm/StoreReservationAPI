package zerobase.storereservationapi.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.type.ReservationType;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ReservationDto {
    private ReservationType reservationType;
    private String storeName;
    private LocalDate date;
    private LocalTime time;
    private String phoneNumber;
    private String reservationId;

    public static ReservationDto toDto(Reservation reservation) {
        return ReservationDto.builder()
                .reservationType(reservation.getReservationType())
                .storeName(reservation.getStore().getName())
                .date(reservation.getDate())
                .time(reservation.getTime())
                .phoneNumber(reservation.getPhoneNumber())
                .reservationId(reservation.getReservationId())
                .build();
    }
}
