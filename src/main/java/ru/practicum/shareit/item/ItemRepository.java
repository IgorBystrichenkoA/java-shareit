package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Optional<Item> get(Long id);

    Item create(ItemCreateDto itemCreateDto);

    Item update(Long itemId, Item itemUpdateDto);

    List<Item> getByUserId(Long userId);

    List<Item> search(String text);
}
