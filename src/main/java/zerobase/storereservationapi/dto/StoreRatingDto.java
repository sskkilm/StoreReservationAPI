package zerobase.storereservationapi.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.embedded.Location;

@Getter
@Builder
public class StoreRatingDto {
    private String name;
    private Location location;
    private Double rating;
}
