package zerobase.storereservationapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.CreateReview;
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
    public ResponseEntity<?> updateReview(
            @PathVariable Long id,
            @RequestBody UpdateReview.Request request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }
}
