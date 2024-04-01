package zerobase.storereservationapi.dto;

import lombok.Builder;
import lombok.Getter;
import zerobase.storereservationapi.domain.Review;
import zerobase.storereservationapi.embedded.Location;

public class DeleteReview {
    @Getter
    @Builder
    public static class Response {
        private String storeName;
        private Location storeLocation;
        private String reservationId;
        private Double rating;
        private String message;

        public static Response toDto(Review review) {
            return Response.builder()
                    .storeName(review.getStore().getName())
                    .storeLocation(review.getStore().getLocation())
                    .reservationId(review.getReservationId())
                    .rating(review.getRating())
                    .message(review.getMessage())
                    .build();
        }
    }
}
