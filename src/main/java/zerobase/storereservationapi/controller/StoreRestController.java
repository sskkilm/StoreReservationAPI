package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.*;
import zerobase.storereservationapi.service.StoreService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreRestController {

    private final StoreService storeService;

    /**
     * 매장 등록
     */
    @PostMapping("/stores")
    public ResponseEntity<RegisterStore.Response> registerStore(
            @RequestBody @Valid RegisterStore.Request request
    ) {
        return ResponseEntity.ok(storeService.registerStore(request));
    }

    /**
     * 매장 수정
     */
    @PutMapping("/stores/{id}")
    public ResponseEntity<UpdateStore.Response> updateStore(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStore.Request request
    ) {
        return ResponseEntity.ok(storeService.updateStore(id, request));
    }

    /**
     * 매장 삭제
     */
    @DeleteMapping("/stores/{id}")
    public ResponseEntity<DeleteStore.Response> deleteStore(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(storeService.deleteStore(id));
    }

    /**
     * 매장 상세 정보 조회
     */
    @GetMapping("/stores/{id}")
    public ResponseEntity<StoreDto> getStoreDetails(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(storeService.getStoreDetails(id));
    }

    /**
     * 매장 목록 조회 (가나다순)
     */
    @GetMapping("/stores/alphabet")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByAlphabet() {
        return ResponseEntity.ok(storeService.getStoreListOrderByAlphabet());
    }

    /**
     * 매장 목록 조회 (거리순)
     */
    @GetMapping("/stores/distance")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        return ResponseEntity.ok(storeService.getStoreListOrderByDistance(latitude, longitude));
    }

    /**
     * 매장 목록 조회 (별점순)
     */
    @GetMapping("/stores/rating")
    public ResponseEntity<List<StoreRatingDto>> getStoreListOrderByRating() {
        return ResponseEntity.ok(storeService.getStoreListOrderByRating());
    }
}
