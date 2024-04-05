package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * 권한 : 고객
     */
    @PostMapping("/reviews")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CreateReview.Response> createReview(
            @RequestBody @Valid CreateReview.Request request
    ) {
        return ResponseEntity.ok(reviewService.createReview(request));
    }

    /**
     * 리뷰 수정
     * 권한 : 고객
     */
    @PutMapping("/reviews/{id}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<UpdateReview.Response> updateReview(
            @PathVariable Long id,
            @RequestBody @Valid UpdateReview.Request request
    ) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    /**
     * 리뷰 삭제
     * 권한 : 고객, 관리자
     */
    @DeleteMapping("/reviews/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    public ResponseEntity<DeleteReview.Response> deleteReview(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reviewService.deleteReview(id));
    }
}
