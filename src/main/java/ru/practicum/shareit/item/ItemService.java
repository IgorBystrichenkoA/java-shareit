package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

public interface ItemService {

    Item create(Long ownerId, ItemCreateDto itemCreateDto);

    Item update(Long itemId, Long ownerId, ItemUpdateDto itemUpdateDto);

    Item getById(Long id);

    List<Item> getByUserId(Long userId);

    List<Item> search(String text);
}
