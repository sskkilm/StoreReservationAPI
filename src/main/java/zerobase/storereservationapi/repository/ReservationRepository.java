package zerobase.storereservationapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.storereservationapi.domain.Reservation;
import zerobase.storereservationapi.domain.Store;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByDate(LocalDate date);

    List<Reservation> findByStoreAndDate(Store store, LocalDate date);
}
