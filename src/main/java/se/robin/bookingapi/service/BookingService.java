package se.robin.bookingapi.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import se.robin.bookingapi.model.Booking;
import se.robin.bookingapi.model.BookingStatus;
import se.robin.bookingapi.repository.BookingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingService {

    private final BookingRepository bookingRepository;

    // Hämta alla bokningar
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // Hämta bokningar med ID
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Booking not found!: " + id
                ));
    }

    // Skapa bokning
    @Transactional
    public Booking createBooking(Booking booking) {
        booking.setId(null);

        if (booking.getStatus() == null) {
            booking.setStatus(BookingStatus.PENDING);
        }

        return bookingRepository.save(booking);
    }

    // Uppdatera bokning
    @Transactional
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking booking = getBookingById(id);

        booking.setName(updatedBooking.getName());
        booking.setEmail(updatedBooking.getEmail());
        booking.setDateTime(updatedBooking.getDateTime());
        booking.setNumberOfPeople(updatedBooking.getNumberOfPeople());

        if (updatedBooking.getStatus() != null) {
            booking.setStatus(updatedBooking.getStatus());
        }

        return bookingRepository.save(booking);
    }

    // Ta bort bokning
    @Transactional
    public void deleteBooking(Long id) {
        Booking booking = getBookingById(id);
        bookingRepository.delete(booking);
    }

}
