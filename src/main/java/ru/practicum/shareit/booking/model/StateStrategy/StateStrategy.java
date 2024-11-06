package ru.practicum.shareit.booking.model.StateStrategy;

import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;

import java.util.List;

public interface StateStrategy {
    List<Booking> findAll(Long bookerId);

    List<Booking> findAllOwner(Long ownerId);

    BookingState getStrategyName();
}
