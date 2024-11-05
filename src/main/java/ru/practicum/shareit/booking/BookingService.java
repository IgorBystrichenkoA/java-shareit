package ru.practicum.shareit.booking;

import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.Booking;

import java.util.List;

public interface BookingService {
    Booking create(Long bookerId, BookingCreateDto bookingDto);

    Booking updateStatus(Long userId, Long bookingId, Boolean approved);

    Booking get(Long bookerId, Long bookingId);

    List<Booking> findAll(Long bookerId, String state);

    List<Booking> findAllOwner(Long ownerId, String state);
}
