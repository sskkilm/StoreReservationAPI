package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.RegisterStore;
import zerobase.storereservationapi.dto.UpdateStore;
import zerobase.storereservationapi.service.StoreService;

@RestController
@RequiredArgsConstructor
public class StoreRestController {

    private final StoreService storeService;

    /**
     * 매장 등록
     *
     * @param request
     * @return
     */
    @PostMapping("/stores")
    public ResponseEntity<RegisterStore.Response> registerStore(
            @RequestBody @Valid RegisterStore.Request request
    ) {
        return ResponseEntity.ok(storeService.registerStore(request));
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<UpdateStore.Response> updateStore(
            @PathVariable Long id,
            @RequestBody @Valid UpdateStore.Request request
    ) {
        return ResponseEntity.ok(storeService.updateStore(id, request));
    }
}
