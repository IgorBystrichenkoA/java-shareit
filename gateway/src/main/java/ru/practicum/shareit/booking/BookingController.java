package ru.practicum.shareit.booking;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.booking.dto.BookingCreateDto;

@Controller
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
@Validated
public class BookingController {
	private final BookingClient bookingClient;

	@PostMapping
	public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
							 @Valid @RequestBody BookingCreateDto bookingDto) {
		return bookingClient.bookItem(userId, bookingDto);
	}

	@PatchMapping("/{bookingId}")
	public ResponseEntity<Object> updateStatus(@RequestHeader("X-Sharer-User-Id") Long userId,
								   @PathVariable("bookingId")
								   Long bookingId,
								   @RequestParam(name = "approved") Boolean approved) {
		return bookingClient.updateStatus(userId, bookingId, approved);
	}

	@GetMapping("/{bookingId}")
	public ResponseEntity<Object> findBookingById(@RequestHeader("X-Sharer-User-Id") Long userId,
									  @PathVariable("bookingId")
									  Long bookingId) {
		return bookingClient.getBooking(userId, bookingId);
	}

	@GetMapping
	public ResponseEntity<Object> findAll(@RequestHeader("X-Sharer-User-Id") Long userId,
									@RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
		return bookingClient.getBookings(userId, bookingState);
	}

	@GetMapping("/owner")
	public ResponseEntity<Object> getAllOwner(@RequestHeader("X-Sharer-User-Id") Long ownerId,
										@RequestParam(value = "state", defaultValue = "ALL") String bookingState) {
		return bookingClient.findAllOwner(ownerId, bookingState);
	}
}
