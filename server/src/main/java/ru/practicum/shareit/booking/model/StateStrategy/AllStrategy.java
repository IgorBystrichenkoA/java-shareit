package ru.practicum.shareit.booking.model.StateStrategy;

import org.springframework.stereotype.Component;
import ru.practicum.shareit.booking.BookingRepository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

@Component
public class AllStrategy implements StateStrategy {

    private final BookingRepository bookingRepository;

    public AllStrategy(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Booking> findAll(Long bookerId) {
        return bookingRepository.findAllByBookerId(bookerId);
    }

    @Override
    public List<Booking> findAllOwner(Long ownerId) {
        return bookingRepository.findAllByOwnerId(ownerId);
    }

    @Override
    public BookingState getStrategyName() {
        return BookingState.ALL;
    }
}
