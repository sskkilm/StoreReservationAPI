package zerobase.storereservationapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import zerobase.storereservationapi.dto.CreateReservation;
import zerobase.storereservationapi.dto.ReservationDto;
import zerobase.storereservationapi.dto.VisitCheck;
import zerobase.storereservationapi.service.ReservationService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationRestController {

    private final ReservationService reservationService;

    /**
     * 예약 요청
     * 권한 : 고객
     */
    @PostMapping("/reservations")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<CreateReservation.Response> createReservation(
            @RequestBody @Valid CreateReservation.Request request
    ) {
        return ResponseEntity.ok(reservationService.createReservation(request));
    }

    /**
     * 예약 정보 확인
     * 권한 : 관리자
     */
    @GetMapping("/reservations")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<List<ReservationDto>> getReservationList(
            @RequestParam Long storeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return ResponseEntity.ok(reservationService.getReservationList(storeId, date));
    }

    /**
     * 예약 승인
     * 권한 : 관리자
     */
    @PatchMapping("/reservations/approve/{reservationId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ReservationDto> approveReservation(
            @PathVariable String reservationId
    ) {
        return ResponseEntity.ok(reservationService.approveReservation(reservationId));
    }

    /**
     * 예약 거절
     * 권한 : 관리자
     */
    @PatchMapping("/reservations/refuse/{reservationId}")
    @PreAuthorize("hasRole('ROLE_MANAGER')")
    public ResponseEntity<ReservationDto> refuseReservation(
            @PathVariable String reservationId
    ) {
        return ResponseEntity.ok(reservationService.refuseReservation(reservationId));
    }

    /**
     * 도착 확인
     * 권한 : 고객
     */
    @GetMapping("/reservations/visit")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<ReservationDto> visitCheck(
            @RequestBody VisitCheck.Request request
    ) {
        return ResponseEntity.ok(reservationService.visitCheck(request));
    }
}
