package ru.practicum.shareit.request;

import ru.practicum.shareit.request.dto.ItemRequestCreateDto;

import java.util.List;

public interface ItemRequestService {
    ItemRequest create(Long userId, ItemRequestCreateDto itemRequestDto);

    List<ItemRequest> getAllByUser(Long userId);

    List<ItemRequest> getAll(Long userId);

    ItemRequest get(Long requestId);
}
