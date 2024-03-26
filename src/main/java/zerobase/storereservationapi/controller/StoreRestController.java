package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.storereservationapi.dto.RegisterStore;
import zerobase.storereservationapi.service.StoreService;

@RestController
@RequiredArgsConstructor
public class StoreRestController {

    private final StoreService storeService;

    @PostMapping("/stores")
    public ResponseEntity<RegisterStore.Response> registerStore(
            @RequestBody @Valid RegisterStore.Request request
    ) {
        return ResponseEntity.ok(storeService.registerStore(request));
    }
}
