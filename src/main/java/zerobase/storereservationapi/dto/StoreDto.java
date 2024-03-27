package zerobase.storereservationapi.dto;

import lombok.*;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.embedded.Location;

@Getter
@Builder
public class StoreDto {
    private String name;

    private Location location;

    private String description;

    public static StoreDto toDto(Store store) {
        return StoreDto.builder()
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .build();
    }
}
