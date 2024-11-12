package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.*;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@RequestHeader("X-Sharer-User-Id") Long userId,
                          @Valid @RequestBody ItemCreateDto itemCreateDto) {
        return itemClient.create(userId, itemCreateDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> update(@PathVariable Long itemId,
                         @RequestHeader("X-Sharer-User-Id") Long userId,
                         @Valid @RequestBody ItemUpdateDto itemUpdateDto) {
        return itemClient.update(itemUpdateDto, itemId, userId);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> get(@PathVariable Long itemId) {
        return itemClient.get(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getByUserId(@RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemClient.getByUserId(userId);
    }

    @GetMapping("/search")
    public ResponseEntity<Object> search(@RequestHeader("X-Sharer-User-Id") Long userId,
                                @RequestParam String text) {
        return itemClient.search(text);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> createComment(@RequestHeader("X-Sharer-User-Id") Long userId,
                                                @Valid @RequestBody CommentCreateDto commentCreateDto,
                                                @PathVariable Long itemId) {
        return itemClient.createComment(userId, itemId, commentCreateDto);
    }
}
