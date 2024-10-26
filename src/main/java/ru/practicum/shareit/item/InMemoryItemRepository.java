package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryItemRepository implements ItemRepository {

    private final Map<Long, Item> items = new ConcurrentHashMap<>();
    private long seq = 0L;

    @Override
    public Optional<Item> get(Long id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public Item create(ItemCreateDto itemCreateDto) {
        Item item = new Item(++seq, itemCreateDto.getName(), itemCreateDto.getDescription(), true,
                itemCreateDto.getOwnerId(), null);
        items.put(item.getId(), item);
        return item;
    }

    @Override
    public Item update(Long itemId, ItemUpdateDto itemUpdateDto) {
        Item itemToUpdate = items.get(itemId);
        if (itemToUpdate == null) {
            throw new NotFoundException("Item not found");
        }
        if (itemUpdateDto.getName() != null) {
            itemToUpdate.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            itemToUpdate.setDescription(itemUpdateDto.getDescription());
        }
        return itemToUpdate;
    }

    @Override
    public List<Item> getByUserId(Long userId) {
        return items.values().stream()
                .filter(item -> Objects.equals(item.getOwnerId(), userId))
                .toList();
    }

    @Override
    public List<Item> search(String text) {
        return items.values().stream()
                .filter(item -> item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getDescription().toLowerCase().contains(text.toLowerCase())).toList();
    }

}
