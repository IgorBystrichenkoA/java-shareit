package ru.practicum.shareit.itemtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.item.CommentMapper;
import ru.practicum.shareit.item.ItemController;
import ru.practicum.shareit.item.ItemMapper;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.model.User;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ItemController.class)
@AutoConfigureMockMvc
public class ItemControllerTests {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ItemService itemService;

    @MockBean
    private ItemMapper itemMapper;

    @Autowired
    private MockMvc mvc;

    private ItemCreateDto itemCreateDto;

    private Item item;

    private ItemDto itemDto;

    private ItemUpdateDto itemUpdateDto;

    private Long id = 1L;

    @BeforeEach
    void init() {
        itemCreateDto = ItemCreateDto
                .builder()
                .name("name")
                .description("description")
                .available(true)
                .build();

        item = Item
                .builder()
                .id(1L)
                .name("name")
                .description("description")
                .available(true)
                .build();

        itemDto = ItemDto
                .builder().id(1L)
                .name("name")
                .description("description")
                .available(true)
                .build();

        itemUpdateDto = ItemUpdateDto
                .builder()
                .name("name")
                .description("description")
                .available(true)
                .build();
    }

    @Test
    void getTest() throws Exception {
        when(itemService.get(anyLong()))
                .thenReturn(item);
        when(itemMapper.toItemDto(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items/1")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemDto)));
        verify(itemService, times(1)).get(id);
    }

    @Test
    void getByUserIdTest() throws Exception {
        when(itemService.getByUserId(anyLong()))
                .thenReturn(List.of(item));
        when(itemMapper.toItemDto(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemDto))));
        verify(itemService, times(1)).getByUserId(id);
    }

    @Test
    void createTest() throws Exception {
        doReturn(item).when(itemService).create(anyLong(), any());
        when(itemMapper.toItemDto(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(post("/items")
                        .header("X-Sharer-User-Id", id)
                        .content(mapper.writeValueAsString(itemCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemDto)));
        verify(itemService, times(1)).create(id, itemCreateDto);
    }

    @Test
    void updateTest() throws Exception {
        when(itemService.update(anyLong(), anyLong(), any()))
                .thenReturn(item);
        when(itemMapper.toItemDto(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(patch("/items/1")
                        .content(mapper.writeValueAsString(itemUpdateDto))
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(itemDto)));
        verify(itemService, times(1)).update(id, id, itemUpdateDto);
    }

    @Test
    void searchTest() throws Exception {
        when(itemService.search(anyString()))
                .thenReturn(List.of(item));
        when(itemMapper.toItemDto(any(), anyLong()))
                .thenReturn(itemDto);

        mvc.perform(get("/items/search?name=name")
                        .header("X-Sharer-User-Id", id)
                        .param("text", "sCriPt")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(List.of(itemDto))));
        verify(itemService, times(1)).search(anyString());
    }

    @Test
    void commentCreateTest() throws Exception {
        CommentCreateDto commentCreateDto = CommentCreateDto
                .builder()
                .text("Comment")
                .build();
        Comment comment = Comment
                .builder()
                .id(id)
                .text("Comment")
                .author(User.builder().name("Author").build())
                .build();

        when(itemService.createComment(anyLong(), anyLong(), any()))
                .thenReturn(comment);

        mvc.perform(post("/items/1/comment")
                        .header("X-Sharer-User-Id", id)
                        .content(mapper.writeValueAsString(commentCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(CommentMapper.toCommentDto(comment))));
        verify(itemService, times(1)).createComment(id, id, commentCreateDto);
    }

}
