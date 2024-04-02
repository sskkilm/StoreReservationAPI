package zerobase.storereservationapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.embedded.Location;

public class RegisterStore {
    @Getter
    public static class Request {
        @NotNull
        private String name;
        @NotNull
        @Valid
        private Location location;
        @NotNull
        private String description;

        public static Store toEntity(Request request) {
            return Store.builder()
                    .name(request.getName())
                    .location(request.getLocation())
                    .description(request.getDescription())
                    .ratingSum(0.0)
                    .reviewCount(0)
                    .build();
        }
    }

    @Getter
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private Location location;
        private String description;

        public static Response toDto(Store store) {
            return Response.builder()
                    .id(store.getId())
                    .name(store.getName())
                    .location(store.getLocation())
                    .description(store.getDescription())
                    .build();
        }
    }
}
