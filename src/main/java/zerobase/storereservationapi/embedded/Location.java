package zerobase.storereservationapi.embedded;

import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class Location {
    private Double latitude;
    private Double longitude;
}
