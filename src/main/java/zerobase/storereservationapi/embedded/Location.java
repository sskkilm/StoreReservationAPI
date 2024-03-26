package zerobase.storereservationapi.embedded;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Embeddable
@Getter
@NoArgsConstructor
public class Location {
    @NotNull
    @DecimalMin("-90.0") // 위도의 최솟값
    @DecimalMax("90.0")  // 위도의 최댓값
    private Double latitude;
    @NotNull
    @DecimalMin("-180.0") // 경도의 최솟값
    @DecimalMax("180.0")  // 경도의 최댓값
    private Double longitude;
}
