package zerobase.storereservationapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zerobase.storereservationapi.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    boolean existsByReservationId(String reservationId);
}
