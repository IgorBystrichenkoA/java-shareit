package ru.practicum.shareit.itemrequesttests;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestMapper;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
public class ItemRequestServiceTests {

    private final ItemRequestService itemRequestService;

    private ItemRequestCreateDto itemRequestCreateDto;

    @BeforeEach
    void init() {
        itemRequestCreateDto = ItemRequestCreateDto
                .builder()
                .description("item request description")
                .build();
    }

    @Test
    void getTest() {
        assertDoesNotThrow(() -> itemRequestService.get(1L));
        ItemRequestDto itemRequestFromService =  ItemRequestMapper.toItemRequestDto(itemRequestService.get(1L));
        ItemRequestDto itemRequestDto = getFirstTestItemRequestDto();
        assertEquals(itemRequestDto.getId(), itemRequestFromService.getId());
        assertEquals(itemRequestDto.getRequestor().getId(), itemRequestFromService.getRequestor().getId());
    }

    @Test
    void createTest() {
        ItemRequestDto itemRequestFromService =  ItemRequestMapper.toItemRequestDto(itemRequestService
                .create(1L, itemRequestCreateDto));
        assertEquals(getCreatedTestItemRequestDto(getFirstTestUser()).getId(), itemRequestFromService.getId());
    }

    @Test
    void getAllByUserTest() {
        List<ItemRequest> itemRequests = itemRequestService.getAllByUser(1L);
        assertEquals(1, itemRequests.size());
    }

    @Test
    void getAllTest() {
        List<ItemRequest> itemRequests = itemRequestService.getAll(1L);
        assertEquals(2, itemRequests.size());
    }

    private ItemRequestDto getCreatedTestItemRequestDto(User requestor) {
        return ItemRequestDto
                .builder()
                .id(4L)
                .description("Заявка 1")
                .created(LocalDateTime.of(2024, 1, 14, 12, 34, 56))
                .requestor(requestor)
                .build();
    }

    private ItemRequestDto getFirstTestItemRequestDto() {
        return ItemRequestDto
                .builder()
                .id(1L)
                .description("Заявка 1")
                .created(LocalDateTime.of(2024, 1, 14, 12, 34, 56))
                .requestor(getFirstTestUser())
                .build();
    }

    private User getFirstTestUser() {
        return User
                .builder()
                .id(1L)
                .name("Андрей")
                .email("an@gmail.com")
                .build();
    }

}
