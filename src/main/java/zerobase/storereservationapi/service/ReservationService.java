package zerobase.storereservationapi.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.domain.Store;
import zerobase.storereservationapi.dto.CreateReservation;
import zerobase.storereservationapi.dto.ReservationDto;
import zerobase.storereservationapi.dto.VisitCheck;
import zerobase.storereservationapi.exception.CustomException;
import zerobase.storereservationapi.repository.ReservationRepository;
import zerobase.storereservationapi.repository.StoreRepository;
import zerobase.storereservationapi.type.ReservationType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static zerobase.storereservationapi.type.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;

    public CreateReservation.Response createReservation(CreateReservation.Request request) {
        // 예약하고자 하는 식당이 없는 경우 예외 처리
        Store store = storeRepository.findById(request.getStoreId())
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        // 매장 이용 시간 2시간으로 제한
        // 요청으로 들어온 시간 기준 2시간 전후 내에 승인된 예약이 이미 있을 경우 예외 처리
        List<Reservation> reservations = reservationRepository.findAllByDate(request.getDate());
        for (Reservation reservation : reservations) {
            if (reservation.getReservationType() == ReservationType.APPROVED &&
                    reservation.getTime().isBefore(request.getTime()) &&
                    !reservation.getTime().plusHours(2).isBefore(request.getTime())
            ) {
                throw new CustomException(ALREADY_RESERVED);
            } else if (reservation.getReservationType() == ReservationType.APPROVED &&
                    reservation.getTime().isAfter(request.getTime()) &&
                    !reservation.getTime().minusHours(2).isAfter(request.getTime())
            ) {
                throw new CustomException(ALREADY_RESERVED);
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
                .orElseThrow(() -> new CustomException(STORE_NOT_FOUND));

        List<Reservation> reservationList = reservationRepository.findByStoreAndDate(store, date);

        return reservationList.stream().map(ReservationDto::toDto).collect(Collectors.toList());
    }

    @Transactional
    public ReservationDto approveReservation(String reservationId) {
        // 존재하지 않는 예약일 경우 예외 처리
        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 이미 처리된 예약일 경우 예외 처리
        if (reservation.getReservationType() != ReservationType.WAITING) {
            throw new CustomException(RESERVATION_ALREADY_PROCESSED);
        }

        reservation.updateReservationTypeToApproved();

        return ReservationDto.toDto(reservation);
    }

    @Transactional
    public ReservationDto refuseReservation(String reservationId) {
        // 존재하지 않는 예약일 경우 예외 처리
        Reservation reservation = reservationRepository.findByReservationId(reservationId)
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 이미 처리된 예약일 경우 예외 처리
        if (reservation.getReservationType() != ReservationType.WAITING) {
            throw new CustomException(RESERVATION_ALREADY_PROCESSED);
        }

        reservation.updateReservationTypeToRefused();

        return ReservationDto.toDto(reservation);
    }

    public ReservationDto visitCheck(VisitCheck.Request request) {
        // 존재하지 않는 예약일 경우 예외 처리
        Reservation reservation = reservationRepository.findByReservationId(request.getReservationId())
                .orElseThrow(() -> new CustomException(RESERVATION_NOT_FOUND));

        // 승인된 예약이 아닐 경우 예외 처리
        if (reservation.getReservationType() != ReservationType.APPROVED) {
            throw new CustomException(RESERVATION_NOT_APPROVED);
        }
        // 예약 날짜가 일치하지 않는 경우 예외 처리
        if (!request.getArrivalDate().equals(reservation.getDate())) {
            throw new CustomException(RESERVATION_DATE_UN_MATCH);
        }
        // 예약 시간 10분 전에 도착하지 못한 경우 예외 처리
        if (request.getArrivalTime().plusMinutes(10).isAfter(reservation.getTime())) {
            throw new CustomException(ARRIVAL_TIME_HAS_PASSED);
        }

        return ReservationDto.toDto(reservation);
    }
}
