package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import zerobase.storereservationapi.dto.CreateReservation;
import zerobase.storereservationapi.service.ReservationService;

@RestController
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    /**
     * 예약 요청
     * @param request
     * @return
     */
    @PostMapping("/reservations")
    public ResponseEntity<CreateReservation.Response> createReservation(
            @RequestBody @Valid CreateReservation.Request request
    ) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }
}
