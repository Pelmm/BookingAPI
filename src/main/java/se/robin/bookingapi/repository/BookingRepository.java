package se.robin.bookingapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.robin.bookingapi.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
