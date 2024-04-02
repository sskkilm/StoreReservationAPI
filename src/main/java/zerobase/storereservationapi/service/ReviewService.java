package zerobase.storereservationapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.domain.Review;
import zerobase.storereservationapi.dto.CreateReview;
import zerobase.storereservationapi.dto.DeleteReview;
import zerobase.storereservationapi.dto.UpdateReview;
import zerobase.storereservationapi.repository.ReservationRepository;
import zerobase.storereservationapi.repository.ReviewRepository;
import zerobase.storereservationapi.type.ReservationType;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public CreateReview.Response createReview(CreateReview.Request request) {
        // 존재하지 않는 예약일 경우 예외 처리
        Reservation reservation = reservationRepository.findByReservationId(request.getReservationId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 예약입니다."));
        // 이미 작성된 리뷰가 있는 경우 예외 처리
        if (reviewRepository.existsByReservationId(reservation.getReservationId())) {
            throw new RuntimeException("이미 리뷰가 존재합니다.");
        }
        // 리뷰를 남기려는 예약이 승인된 예약이 아닐 경우 예외 처리
        if (reservation.getReservationType() != ReservationType.APPROVED) {
            throw new RuntimeException("리뷰 권한이 없습니다.");
        }

        Review review = Review.builder()
                .store(reservation.getStore())
                .reservationId(reservation.getReservationId())
                .rating(request.getRating())
                .message(request.getMessage())
                .build();
        
        // 매장 리뷰 리스트에 리뷰 추가
        reservation.getStore().addReview(review);

        return CreateReview.Response.toDto(reviewRepository.save(review));
    }

    @Transactional
    public UpdateReview.Response updateReview(Long id, UpdateReview.Request request) {
        // 리뷰가 존재하지 않을 경우 예외 처리
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));

        review.updateRatingAndMessage(request.getRating(), request.getMessage());

        // 매장 리뷰 리스트에 리뷰 수정
        List<Review> reviewList = review.getStore().getReviewList();
        for (int i = 0; i < reviewList.size(); i++) {
            if (Objects.equals(reviewList.get(i).getId(), review.getId())) {
                reviewList.set(i, review);
                break;
            }
        }

        return UpdateReview.Response.toDto(review);
    }

    @Transactional
    public DeleteReview.Response deleteReview(Long id) {
        // 리뷰가 존재하지 않을 경우 예외 처리
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 리뷰입니다."));

        reviewRepository.delete(review);

        // 매장 리뷰 리스트에 리뷰 삭제
        List<Review> reviewList = review.getStore().getReviewList();
        for (int i = 0; i < reviewList.size(); i++) {
            if (Objects.equals(reviewList.get(i).getId(), review.getId())) {
                reviewList.remove(i);
                break;
            }
        }

        return DeleteReview.Response.toDto(review);
    }
}
