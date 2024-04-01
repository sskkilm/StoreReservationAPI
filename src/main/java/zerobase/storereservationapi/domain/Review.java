package zerobase.storereservationapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Store store;

    private String reservationId;

    private Double rating;

    private String message;

    public void updateRatingAndMessage(Double rating, String message) {
        this.rating = rating;
        this.message = message;
    }
}
