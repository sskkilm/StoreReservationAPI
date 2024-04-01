package zerobase.storereservationapi.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Review;
import zerobase.storereservationapi.embedded.Location;

public class UpdateReview {
    @Getter
    public static class Request {
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
        private String storeName;
        private Location storeLocation;
        private Double rating;
        private String message;

        public static UpdateReview.Response toDto(Review review) {
            return UpdateReview.Response.builder()
                    .storeName(review.getStore().getName())
                    .storeLocation(review.getStore().getLocation())
                    .rating(review.getRating())
                    .message(review.getMessage())
                    .build();
        }
    }
}
