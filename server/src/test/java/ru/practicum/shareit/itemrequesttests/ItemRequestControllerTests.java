package ru.practicum.shareit.itemrequesttests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestController;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemRequestController.class)
@AutoConfigureMockMvc
public class ItemRequestControllerTests {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemRequestService itemRequestService;

    @Autowired
    private MockMvc mvc;

    private ItemRequestCreateDto itemRequestCreateDto;

    private ItemRequest itemRequest;

    private ItemRequestDto itemRequestDto;

    private Long id = 1L;

    @BeforeEach
    void init() {
        itemRequestCreateDto = ItemRequestCreateDto
                .builder()
                .description("item request description")
                .build();

        itemRequest = ItemRequest
                .builder()
                .id(id)
                .description("item request description")
                .build();

        itemRequestDto = ItemRequestDto
                .builder()
                .id(id)
                .description("item request description")
                .build();
    }

    @Test
    void createTest() throws Exception {
        doReturn(itemRequest).when(itemRequestService).create(anyLong(), any());

        mvc.perform(post("/requests")
                        .header("X-Sharer-User-Id", id)
                        .content(mapper.writeValueAsString(itemRequestCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemRequestDto)));
        verify(itemRequestService, times(1)).create(id, itemRequestCreateDto);
    }

    @Test
    void getAllByUserTest() throws Exception {
        doReturn(List.of(itemRequest)).when(itemRequestService).getAllByUser(anyLong());

        mvc.perform(get("/requests")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemRequestDto))));
        verify(itemRequestService, times(1)).getAllByUser(id);
    }

    @Test
    void getAllTest() throws Exception {
        doReturn(List.of(itemRequest)).when(itemRequestService).getAll(anyLong());

        mvc.perform(get("/requests/all")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemRequestDto))));
        verify(itemRequestService, times(1)).getAll(id);
    }

    @Test
    void getByIdTest() throws Exception {
        when(itemRequestService.get(anyLong()))
                .thenReturn(itemRequest);

        mvc.perform(get("/requests/1")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemRequestDto)));
        verify(itemRequestService, times(1)).get(id);
    }
}
