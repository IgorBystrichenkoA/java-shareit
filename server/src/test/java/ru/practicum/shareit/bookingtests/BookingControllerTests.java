package ru.practicum.shareit.bookingtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.booking.BookingController;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = BookingController.class)
@AutoConfigureMockMvc
public class BookingControllerTests {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookingService bookingService;

    @Autowired
    private MockMvc mvc;

    private BookingCreateDto bookingCreateDto;

    private Booking booking;

    private BookingDto bookingDto;

    private Long id = 1L;

    @BeforeEach
    void init() {
        LocalDateTime start = LocalDateTime.of(2020, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2020, 1, 1, 0, 0, 1);

        bookingCreateDto = BookingCreateDto
                .builder()
                .start(start)
                .end(end)
                .itemId(1L)
                .build();

        booking = Booking
                .builder()
                .id(id)
                .startDate(start)
                .endDate(end)
                .status(BookingStatus.APPROVED)
                .build();

        bookingDto = BookingDto
                .builder()
                .id(id)
                .start(start)
                .end(end)
                .status(BookingStatus.APPROVED.name())
                .build();
    }

    @Test
    void createTest() throws Exception {
        doReturn(booking).when(bookingService).create(anyLong(), any());

        mvc.perform(post("/bookings")
                        .header("X-Sharer-User-Id", id)
                        .content(mapper.writeValueAsString(bookingCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
        verify(bookingService, times(1)).create(id, bookingCreateDto);
    }

    @Test
    void updateStatusTest() throws Exception {
        when(bookingService.updateStatus(anyLong(), anyLong(), anyBoolean()))
                .thenReturn(booking);

        mvc.perform(patch("/bookings/1")
                        .header("X-Sharer-User-Id", id)
                        .param("approved", "true")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
        verify(bookingService, times(1)).updateStatus(id, id, true);
    }

    @Test
    void getByIdTest() throws Exception {
        when(bookingService.get(anyLong(), anyLong()))
                .thenReturn(booking);

        mvc.perform(get("/bookings/1")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(bookingDto)));
        verify(bookingService, times(1)).get(id, id);
    }

    @Test
    void findAllTest() throws Exception {
        doReturn(List.of(booking)).when(bookingService).findAll(anyLong(), anyString());

        mvc.perform(get("/bookings")
                        .header("X-Sharer-User-Id", id)
                        .param("state", "ALL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(bookingDto))));
        verify(bookingService, times(1)).findAll(id, "ALL");
    }

    @Test
    void getAllTest() throws Exception {
        doReturn(List.of(booking)).when(bookingService).findAllOwner(anyLong(), anyString());
        List<BookingDto> list = List.of(bookingDto);

        mvc.perform(get("/bookings/owner")
                        .header("X-Sharer-User-Id", id)
                        .param("state", "ALL")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(list)));
        verify(bookingService, times(1)).findAllOwner(id, "ALL");
    }
}
