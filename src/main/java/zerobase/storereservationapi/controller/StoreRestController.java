package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.DeleteStore;
import zerobase.storereservationapi.dto.RegisterStore;
import zerobase.storereservationapi.dto.StoreDto;
import zerobase.storereservationapi.dto.UpdateStore;
import zerobase.storereservationapi.service.StoreService;

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
}
