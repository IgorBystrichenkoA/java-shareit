package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    private final ItemMapper mapper;

    @PostMapping
    public ItemDto create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @RequestBody ItemCreateDto itemCreateDto) {
        return mapper.toItemDto(itemService.create(userId, itemCreateDto), userId);
    }

    @PatchMapping("/{itemId}")
    public ItemDto update(@PathVariable Long itemId,
                         @RequestHeader("X-Sharer-User-Id") Long userId,
                         @RequestBody ItemUpdateDto itemUpdateDto) {
        return mapper.toItemDto(itemService.update(itemId, userId, itemUpdateDto), userId);
    }

    @GetMapping("/{itemId}")
    public ItemDto get(@PathVariable Long itemId,
                       @RequestHeader("X-Sharer-User-Id") Long userId) {
        return mapper.toItemDto(itemService.get(itemId), userId);
    }

    @GetMapping
    public List<ItemDto> getByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemService.getByUserId(userId).stream().map(item -> mapper.toItemDto(item, userId)).toList();
    }

    @GetMapping("/search")
    public List<ItemDto> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @RequestParam String text) {
        return itemService.search(text).stream().map(item -> mapper.toItemDto(item, userId)).toList();
    }

    @PostMapping("/{itemId}/comment")
    public CommentDto createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                    @RequestBody CommentCreateDto commentCreateDto,
                                    @PathVariable Long itemId) {
        return CommentMapper.toCommentDto(itemService.createComment(userId, itemId, commentCreateDto));
    }
}
