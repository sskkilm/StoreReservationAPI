package zerobase.storereservationapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.storereservationapi.dto.CreateReview;
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
}
