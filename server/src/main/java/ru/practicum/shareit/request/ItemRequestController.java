package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.util.List;

@RestController
@RequestMapping(path = "/requests")
@RequiredArgsConstructor
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(
            @RequestHeader("X-Sharer-User-Id") Long userId,
            @RequestBody ItemRequestCreateDto itemRequestCreateDto) {
        return ItemRequestMapper.toItemRequestDto(itemRequestService.create(userId, itemRequestCreateDto));
    }

    @GetMapping
    public List<ItemRequestDto> getAllByUser(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAllByUser(userId).stream().map(ItemRequestMapper::toItemRequestDto).toList();
    }

    @GetMapping("/all")
    public List<ItemRequestDto> getAll(
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return itemRequestService.getAll(userId).stream().map(ItemRequestMapper::toItemRequestDto).toList();
    }

    @GetMapping("/{requestId}")
    public ItemRequestDto getById(
            @PathVariable Long requestId,
            @RequestHeader("X-Sharer-User-Id") Long userId) {
        return ItemRequestMapper.toItemRequestDto(itemRequestService.get(requestId));
    }
}
