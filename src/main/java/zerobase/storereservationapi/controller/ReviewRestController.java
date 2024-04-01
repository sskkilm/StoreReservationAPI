package zerobase.storereservationapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.CreateReview;
import zerobase.storereservationapi.dto.DeleteReview;
import zerobase.storereservationapi.dto.UpdateReview;
import zerobase.storereservationapi.service.ReviewService;

@RestController
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    /**
     * 리뷰 등록
     */
    @PostMapping("/reviews")
    public ResponseEntity<CreateReview.Response> createReview(
            @RequestBody CreateReview.Request request
    ) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }

    /**
     * 리뷰 수정
     */
    @PutMapping("/reviews/{id}")
    public ResponseEntity<UpdateReview.Response> updateReview(
            @PathVariable Long id,
            @RequestBody UpdateReview.Request request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    /**
     * 리뷰 삭제
     */
    @DeleteMapping("/reviews/{id}")
    public ResponseEntity<DeleteReview.Response> deleteReview(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }
}
