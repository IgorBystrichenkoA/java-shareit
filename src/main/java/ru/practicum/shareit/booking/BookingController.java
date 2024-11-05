package ru.practicum.shareit.booking;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService bookingService;

    @PostMapping
    public BookingDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                             @Valid @RequestBody BookingCreateDto bookingDto) {
        return BookingMapper.toBookingDto(bookingService.create(userId, bookingDto));
    }

    @PatchMapping("/{bookingId}")
    public BookingDto updateStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
                                      @PathVariable("bookingId")
                                      Long bookingId,
                                      @RequestParam(name = "approved") Boolean approved) {
        return BookingMapper.toBookingDto(bookingService.updateStatus(userId, bookingId, approved));
    }

    @GetMapping("/{bookingId}")
    public BookingDto findBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
                                         @PathVariable("bookingId")
                                         Long bookingId) {
        return BookingMapper.toBookingDto(bookingService.get(userId, bookingId));
    }

    @GetMapping
    public List<BookingDto> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
                                       @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        return bookingService.findAll(userId, bookingState).stream()
                .map(BookingMapper::toBookingDto)
                .toList();
    }

    @GetMapping("/owner")
    public List<BookingDto> getAllOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
                                        @RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
        return bookingService.findAllOwner(ownerId, bookingState).stream()
                .map(BookingMapper::toBookingDto)
                .toList();
    }
}
