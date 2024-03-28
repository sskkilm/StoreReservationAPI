package zerobase.storereservationapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.dto.CreateReservation;
import zerobase.storereservationapi.dto.ReservationDto;
import zerobase.storereservationapi.repository.ReservationRepository;
import zerobase.storereservationapi.repository.StoreRepository;
import zerobase.storereservationapi.type.ReservationType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    public CreateReservation.Response createReservation(CreateReservation.Request request) {
        // 예약하고자 하는 식당이 없는 경우 예외 처리
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));

        // 매장 이용 시간 2시간으로 제한
        // 요청으로 들어온 시간 기준 2시간 전후 내에 승인된 예약이 이미 있을 경우 예외 처리
        List<Reservation> reservations = reservationRepository.findAllByDate(request.getDate());
        for (Reservation reservation : reservations) {
            if (reservation.getReservationType() == ReservationType.APPROVED &&
                    reservation.getTime().isBefore(request.getTime()) &&
                    !reservation.getTime().plusHours(2).isBefore(request.getTime())
            ) {
                throw new RuntimeException("이미 예약된 시간입니다.");
            } else if (reservation.getReservationType() == ReservationType.APPROVED &&
                    reservation.getTime().isAfter(request.getTime()) &&
                    !reservation.getTime().minusHours(2).isAfter(request.getTime())
            ) {
                throw new RuntimeException("이미 예약된 시간입니다.");
            }
        }

        return CreateReservation.Response.toDto(
                reservationRepository.save(Reservation.builder()
                        .reservationType(ReservationType.WAITING)
                        .store(store)
                        .date(request.getDate())
                        .time(request.getTime())
                        .phoneNumber(request.getPhoneNumber())
                        .reservationId(UUID.randomUUID().toString().replace("-", ""))
                        .build())
        );
    }

    public List<ReservationDto> getReservationList(Long storeId, LocalDate date) {
        // 확인하고자 하는 매장이 없는 경우 예외 처리
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new RuntimeException("없는 매장입니다."));

        List<Reservation> reservationList = reservationRepository.findByStoreAndDate(store, date);

        return reservationList.stream().map(ReservationDto::toDto).collect(Collectors.toList());
    }
}
