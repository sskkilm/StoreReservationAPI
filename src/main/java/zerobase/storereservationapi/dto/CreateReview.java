package zerobase.storereservationapi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Review;
import zerobase.storereservationapi.embedded.Location;

public class CreateReview {
    @Getter
    public static class Request{
        @NotNull
        private String reservationId;
        @NotNull
        @DecimalMin(value = "0.0")
        @DecimalMax(value = "5.0")
        private Double rating;
        @NotNull
        private String message;
    }
    @Getter
    @Builder
    public static class Response{
        private Long id;
        private String storeName;
        private Location storeLocation;
        private String reservationId;
        private Double rating;
        private String message;

        public static Response toDto(Review review) {
            return Response.builder()
                    .id(review.getId())
                    .storeName(review.getStore().getName())
                    .storeLocation(review.getStore().getLocation())
                    .reservationId(review.getReservationId())
                    .rating(review.getRating())
                    .message(review.getMessage())
                    .build();
        }
    }
}
