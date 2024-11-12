package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestClient itemRequestClient;

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        return itemRequestClient.create(userId, itemRequestCreateDto);
    }

    @GetMapping
    public ResponseEntity<Object> getAllByUser(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getAllByUser(userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.getAll(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(
            @PathVariable Long requestId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestClient.get(requestId);
    }
}
