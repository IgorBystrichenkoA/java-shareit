package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public Item create(Long ownerId, ItemCreateDto itemCreateDto) {
        itemCreateDto.setOwnerId(ownerId);
        return itemRepository.create(itemCreateDto);
    }

    @Override
    public Item update(Long itemId, Long ownerId, ItemUpdateDto itemUpdateDto) {
        Item dbItem = itemRepository.get(itemId).orElseThrow(() -> new NotFoundException("Item not found"));
        if (itemUpdateDto.getName() != null) {
            dbItem.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            dbItem.setDescription(itemUpdateDto.getDescription());
        }
        dbItem.setOwnerId(ownerId);
        return itemRepository.update(itemId, dbItem);
    }

    @Override
    public Item getById(Long id) {
        return itemRepository.get(id).orElseThrow(() -> new NotFoundException("Предмет не найден"));
    }

    @Override
    public List<Item> getByUserId(Long userId) {
        return itemRepository.getByUserId(userId);
    }

    @Override
    public List<Item> search(String text) {
        return itemRepository.search(text);
    }
}
