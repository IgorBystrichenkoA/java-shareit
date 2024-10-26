package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemCreateDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.dto.ItemUpdateDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody ItemCreateDto itemCreateDto) {

        Item item = itemService.create(userId, itemCreateDto);
        log.info("Создан предмет: {}", item);
        return ItemMapper.toItemDto(item);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable Long itemId,
                         @RequestHeader("X-Sharer-User-Id") Long userId,
                         @Valid @RequestBody ItemUpdateDto itemUpdateDto) {

        Item item = itemService.update(itemId, userId, itemUpdateDto);
        log.info("Обновлен предмет: {}", item);
        return ItemMapper.toItemDto(item);
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@PathVariable Long itemId) {

        Item item = itemService.getById(itemId);
        log.info("Получен предмет: {}", item);
        return ItemMapper.toItemDto(item);
    }

    @GetMapping
    public List<ItemDto> getByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {

        List<Item> items = itemService.getByUserId(userId);
        log.info("Получен список предметов пользователя длиной: {}", items.size());
        return items.stream().map(ItemMapper::toItemDto).toList();
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestParam String text) {

        List<Item> items = itemService.search(text);
        log.info("Получен список предметов по тексту: {}", items.size());
        return items.stream().map(ItemMapper::toItemDto).toList();
    }
}
