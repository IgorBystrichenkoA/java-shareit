package ru.practicum.shareit.booking.model.StateStrategy;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

@Component
public class RejectedStrategy implements StateStrategy {

    private final BookingRepository bookingRepository;

    public RejectedStrategy(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll(Long bookerId) {
        return bookingRepository.findRejectedByBookerId(bookerId);
    }

    @Override
    public List<Booking> findAllOwner(Long ownerId) {
        return bookingRepository.findRejectedByOwnerId(ownerId);
    }

    @Override
    public BookingState getStrategyName() {
        return BookingState.REJECTED;
    }
}
