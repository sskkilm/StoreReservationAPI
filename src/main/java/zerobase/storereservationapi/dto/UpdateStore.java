package zerobase.storereservationapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.embedded.Location;

public class UpdateStore {
    @Getter
    public static class Request {
        @NotNull
        private String name;
        @NotNull
        @Valid
        private Location location;
        @NotNull
        private String description;
    }

    @Getter
    @Builder
    public static class Response {
        private String name;
        private Location location;
        private String description;

        public static Response toDto(Store store) {
            return Response.builder()
                    .name(store.getName())
                    .location(store.getLocation())
                    .description(store.getDescription())
                    .build();
        }
    }
}
