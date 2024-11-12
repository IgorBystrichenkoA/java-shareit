package ru.practicum.shareit.booking;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.client.BaseClient;

public class BookingClient extends BaseClient {

    public BookingClient(RestTemplate rest) {
        super(rest);
    }

    public ResponseEntity<Object> getBookings(long userId, String state) {
        Map<String, Object> parameters = Map.of(
                "state", state
        );
        return get("?state={state}", userId, parameters);
    }

    public ResponseEntity<Object> create(long userId, BookingCreateDto requestDto) {
        return post("", userId, requestDto);
    }

    public ResponseEntity<Object> getById(long userId, Long bookingId) {
        return get("/" + bookingId, userId);
    }

    public ResponseEntity<Object> updateStatus(long userId, Long bookingId, Boolean approved) {
        return patch("/" + bookingId + "?approved=" + approved, userId);
    }

    public ResponseEntity<Object> findAllOwner(long ownerId, String bookingState) {
        Map<String, Object> parameters = Map.of(
                "state", bookingState
        );
        return get("/owner?state={state}", ownerId, parameters);
    }
}
