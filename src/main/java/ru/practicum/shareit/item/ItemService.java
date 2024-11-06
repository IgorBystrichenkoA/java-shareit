package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item get(Long id);

    Item create(Long ownerId, ItemCreateDto itemCreateDto);

    Item update(Long itemId, Long ownerId, ItemUpdateDto itemUpdateDto);

    List<Item> getByUserId(Long userId);

    List<Item> search(String text);

    Comment createComment(Long userId, Long itemId, CommentCreateDto commentCreateDto);

    List<Comment> findAllByItemId(Long itemId);
}
