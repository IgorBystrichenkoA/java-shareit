package ru.practicum.shareit.itemtests;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.exception.NotAllowedException;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
public class ItemServiceTests {
    private final ItemService itemService;
    private final ItemMapper itemMapper;

    private ItemCreateDto itemCreateDto;

    @BeforeEach
    void init() {
        itemCreateDto = ItemCreateDto.builder()
                .name("Компьютер4")
                .description("Компьютер4")
                .available(true)
                .build();
    }

    @Test
    void getTest() {
        ItemDto itemFromService =  itemMapper.toItemDto(itemService.get(1L), 1L);
        assertEquals(getFirstTestItemDto(), itemFromService);
    }

    @Test
    void createTest() {
        Long userId = 1L;
        itemService.create(userId, itemCreateDto);
        ItemDto itemFromService = itemMapper.toItemDto(itemService.get(4L), -1L);
        assertEquals(getCreatedTestItemDto(), itemFromService);
    }

    @Test
    void updateTest() {
        ItemUpdateDto itemUpdateDto = ItemUpdateDto
                .builder()
                .name("update name")
                .description("update description")
                .available(false)
                .build();

        ItemDto userBeforeUpdate = itemMapper.toItemDto(itemService.get(1L), 3L);;
        ItemDto userAfterService = itemMapper.toItemDto(itemService.update(1L, 3L, itemUpdateDto), 3L);
        userBeforeUpdate.setName("update name");
        userBeforeUpdate.setDescription("update description");
        userBeforeUpdate.setAvailable(false);

        assertEquals(userBeforeUpdate, userAfterService);
    }

    @Test
    void getByUserIdTest() {
        List<Item> itemsFromSerice = itemService.getByUserId(3L);
        assertEquals(1, itemsFromSerice.size());
        assertEquals(getFirstTestItemDto(), itemMapper.toItemDto(itemsFromSerice.getFirst(), 1L));
        itemService.create(3L, itemCreateDto);
        assertEquals(2, itemService.getByUserId(3L).size());
    }

    @Test
    void searchTest() {
        assertDoesNotThrow(() -> itemService.search("Компьютер"));
        List<Item> itemsFromSerice = itemService.search("КомПьют");
        assertEquals(3, itemsFromSerice.size());
    }

    @Test
    void createCommentTest() {
        assertDoesNotThrow(() -> itemService.createComment(1L, 1L,
                CommentCreateDto.builder().text("comment text1").build()));
        assertThrows(NotAllowedException.class,() -> itemService.createComment(2L, 1L,
                CommentCreateDto.builder().text("comment text2").build()));

        List<Comment> commentsFromService = itemService.findCommentsByItemId(1L);

        assertEquals(1, commentsFromService.size());
        assertEquals("comment text1", commentsFromService.getFirst().getText());
    }

    @Test
    void findCommentsByItemIdTest() {
        List<Comment> comments = List.of(
                itemService.createComment(1L, 1L,
                        CommentCreateDto.builder().text("comment text1").build()),
                itemService.createComment(1L, 1L,
                        CommentCreateDto.builder().text("comment text2").build()),
                itemService.createComment(1L, 1L,
                        CommentCreateDto.builder().text("comment text3").build()));

        assertDoesNotThrow(() -> itemService.findCommentsByItemId(1L));
        List<Comment> commentsFromService = itemService.findCommentsByItemId(1L);
        assertEquals(3, commentsFromService.size());
        assertEquals(comments.getFirst().getId(), commentsFromService.getFirst().getId());
    }

    private ItemDto getFirstTestItemDto() {
        return ItemDto
                .builder()
                .id(1L)
                .name("Компьютер1")
                .description("Компьютер1")
                .requestId(1L)
                .available(true)
                .build();
    }

    private ItemDto getCreatedTestItemDto() {
        return ItemDto
                .builder()
                .id(4L)
                .name("Компьютер4")
                .description("Компьютер4")
                .available(true)
                .build();
    }
}
