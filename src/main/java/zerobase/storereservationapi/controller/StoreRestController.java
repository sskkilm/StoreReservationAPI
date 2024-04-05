package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
     * 권한 : 관리자
     */
    @PostMapping("/stores")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<RegisterStore.Response> registerStore(
            @RequestBody @Valid RegisterStore.Request request
    ) {
        return ResponseEntity.ok(storeService.registerStore(request));
    }

    /**
     * 매장 수정
     * 권한 : 관리자
     */
    @PutMapping("/stores/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<UpdateStore.Response> updateStore(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStore.Request request
    ) {
        return ResponseEntity.ok(storeService.updateStore(id, request));
    }

    /**
     * 매장 삭제
     * 권한: 관리자
     */
    @DeleteMapping("/stores/{id}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<DeleteStore.Response> deleteStore(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(storeService.deleteStore(id));
    }

    /**
     * 매장 상세 정보 조회
     * 권한 : 고객, 관리자
     */
    @GetMapping("/stores/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    public ResponseEntity<StoreDto> getStoreDetails(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(storeService.getStoreDetails(id));
    }

    /**
     * 매장 목록 조회 (가나다순)
     * 권한 : 고객, 관리자
     */
    @GetMapping("/stores/alphabet")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByAlphabet() {
        return ResponseEntity.ok(storeService.getStoreListOrderByAlphabet());
    }

    /**
     * 매장 목록 조회 (거리순)
     * 권한 : 고객, 관리자
     */
    @GetMapping("/stores/distance")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    public ResponseEntity<List<StoreDto>> getStoreListOrderByDistance(
            @RequestParam Double latitude,
            @RequestParam Double longitude
    ) {
        return ResponseEntity.ok(storeService.getStoreListOrderByDistance(latitude, longitude));
    }

    /**
     * 매장 목록 조회 (별점순)
     * 권한 : 고객, 관리자
     */
    @GetMapping("/stores/rating")
    @PreAuthorize("hasAnyRole('ROLE_CUSTOMER', 'ROLE_MANAGER')")
    public ResponseEntity<List<StoreRatingDto>> getStoreListOrderByRating() {
        return ResponseEntity.ok(storeService.getStoreListOrderByRating());
    }
}
