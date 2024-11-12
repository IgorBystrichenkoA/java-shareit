package ru.practicum.shareit.bookingtests;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.ForbiddenException;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
public class BookingServiceTests {
    private final BookingService bookingService;

    private BookingCreateDto bookingCreateDto;

    @BeforeEach
    void init() {
        bookingCreateDto = BookingCreateDto
                .builder()
                .start(LocalDateTime.of(2024, 1, 1, 0, 0, 0))
                .end(LocalDateTime.of(2024, 1, 1, 0, 0, 1))
                .itemId(1L)
                .build();
    }

    @Test
    void createTest() {
        Long bookerId = 1L;
        Booking booking = bookingService.create(bookerId, bookingCreateDto);
        BookingDto bookingFromService = BookingMapper.toBookingDto(bookingService.get(bookerId, booking.getId()));
        assertEquals(booking.getId(), bookingFromService.getId());
        assertEquals(BookingStatus.WAITING.name(), booking.getStatus().name());
    }

    @Test
    void updateStatusTest() {
        BookingDto booking = getFirstTestBookingDto();
        assertThrows(ForbiddenException.class,
                () -> bookingService.updateStatus(1L, booking.getId(), false));
        bookingService.updateStatus(3L, booking.getId(), false);
        assertEquals(BookingStatus.REJECTED, bookingService.get(1L, booking.getId()).getStatus());
    }

    @Test
    void getTest() {
        BookingDto booking = getFirstTestBookingDto();
        assertEquals(booking.getId(), BookingMapper.toBookingDto(bookingService.get(1L, 1L)).getId());
    }

    @Test
    void findAllTest() {
        List<Booking> bookings = bookingService.findAll(1L, BookingState.ALL.name());
        assertEquals(1, bookings.size());
        bookings = bookingService.findAll(1L, BookingStatus.WAITING.name());
        assertEquals(0, bookings.size());
        bookingService.create(1L, bookingCreateDto);
        bookingService.create(1L, bookingCreateDto);
        bookings = bookingService.findAll(1L, BookingStatus.WAITING.name());
        assertEquals(2, bookings.size());
    }

    @Test
    void findAllOwnerTest() {
        List<Booking> bookings = bookingService.findAllOwner(1L, BookingState.ALL.name());
        assertEquals(1, bookings.size());
        bookingService.create(1L, bookingCreateDto);
        bookingService.create(1L, bookingCreateDto);
        Long ownerItem1Id = 3L;
        bookings = bookingService.findAllOwner(ownerItem1Id, BookingState.ALL.name());
        assertEquals(3, bookings.size());
    }

    private BookingDto getFirstTestBookingDto() {
        return BookingDto
                .builder()
                .id(1L)
                .start(LocalDateTime.of(2024, 1, 14, 12, 34, 56))
                .end(LocalDateTime.of(2024, 1, 14, 12, 34, 57))
                .status(BookingStatus.APPROVED.name())
                .build();
    }

    private BookingDto getCreatedTestBookingDto() {
        return BookingDto
                .builder()
                .id(4L)
                .start(LocalDateTime.of(2024, 1, 14, 12, 34, 56))
                .end(LocalDateTime.of(2024, 1, 14, 12, 34, 57))
                .status(BookingStatus.WAITING.name())
                .build();
    }
}
