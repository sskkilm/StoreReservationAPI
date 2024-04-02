package zerobase.storereservationapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import zerobase.storereservationapi.type.ReservationType;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReservationType reservationType;

    @ManyToOne
    private Store store;

    private LocalDate date;

    private LocalTime time;

    private String phoneNumber;

    private String reservationId;

    public void updateReservationTypeToApproved() {
        this.reservationType = ReservationType.APPROVED;
    }

    public void updateReservationTypeToRefused() {
        this.reservationType = ReservationType.REFUSED;
    }
}
