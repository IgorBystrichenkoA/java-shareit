package ru.practicum.shareit.item;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.item.dto.ItemCreateDto;
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
    public Item update(Long itemId, Item itemUpdateDto) {
        items.put(itemId, itemUpdateDto);
        return itemUpdateDto;
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
