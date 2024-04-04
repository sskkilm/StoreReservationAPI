package zerobase.storereservationapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.domain.Review;
import zerobase.storereservationapi.dto.CreateReview;
import zerobase.storereservationapi.dto.DeleteReview;
import zerobase.storereservationapi.dto.UpdateReview;
import zerobase.storereservationapi.exception.CustomException;
import zerobase.storereservationapi.repository.ReservationRepository;
import zerobase.storereservationapi.repository.ReviewRepository;
import zerobase.storereservationapi.type.ReservationType;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public CreateReview.Response createReview(CreateReview.Request request) {
        // 존재하지 않는 예약일 경우 예외 처리
        Reservation reservation = reservationRepository.findByReservationId(request.getReservationId())
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));
        // 이미 작성된 리뷰가 있는 경우 예외 처리
        if (reviewRepository.existsByReservationId(reservation.getReservationId())) {
            throw new CustomException(AlREADY_EXIST_REVIEW);
        }
        // 리뷰를 남기려는 예약이 승인된 예약이 아닐 경우 예외 처리
        if (reservation.getReservationType() != ReservationType.APPROVED) {
            throw new CustomException(NO_REVIEW_PERMISSION);
        }

        Review review = Review.builder()
                .store(reservation.getStore())
                .reservationId(reservation.getReservationId())
                .rating(request.getRating())
                .message(request.getMessage())
                .build();

        // 매장 별점 합 업데이트
        reservation.getStore().plusRating(request.getRating());

        return CreateReview.Response.toDto(reviewRepository.save(review));
    }

    @Transactional
    public UpdateReview.Response updateReview(Long id, UpdateReview.Request request) {
        // 리뷰가 존재하지 않을 경우 예외 처리
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        // 매장 별점 합 업데이트
        review.getStore().minusRating(review.getRating());

        review.updateRatingAndMessage(request.getRating(), request.getMessage());

        // 매장 별점 합 업데이트
        review.getStore().plusRating(request.getRating());

        return UpdateReview.Response.toDto(review);
    }

    @Transactional
    public DeleteReview.Response deleteReview(Long id) {
        // 리뷰가 존재하지 않을 경우 예외 처리
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(REVIEW_NOT_FOUND));

        reviewRepository.delete(review);

        // 매장 별점 합 업데이트
        review.getStore().minusRating(review.getRating());

        return DeleteReview.Response.toDto(review);
    }
}
