package ru.practicum.shareit.bookingtests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;
import ru.practicum.shareit.booking.dto.BookingDto;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookingDtoJsonTests {
    @Autowired
    JacksonTester<BookingDto> json;

    @Test
    void testBookingDto() throws Exception {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 1);
        LocalDateTime end = LocalDateTime.of(2020, 1, 1, 0, 0, 2);

        BookingDto bookingDto = BookingDto
                .builder()
                .id(1L)
                .start(start)
                .end(end)
                .build();

        JsonContent<BookingDto> result = json.write(bookingDto);

        assertThat(result).extractingJsonPathNumberValue("$.id").isEqualTo(1);
        assertThat(result).extractingJsonPathStringValue("$.start").isEqualTo(start.toString());
        assertThat(result).extractingJsonPathStringValue("$.end").isEqualTo(end.toString());
    }
}
