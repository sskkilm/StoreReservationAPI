package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.CreateReservation;
import zerobase.storereservationapi.dto.ReservationDto;
import zerobase.storereservationapi.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

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

    /**
     * 예약 정보 확인
     */
    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getReservationList(
            @RequestParam Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(reservationService.getReservationList(storeId, date));
    }
}
