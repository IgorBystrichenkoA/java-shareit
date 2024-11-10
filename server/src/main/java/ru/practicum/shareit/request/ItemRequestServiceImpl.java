package ru.practicum.shareit.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestCreateDto;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemRequestServiceImpl implements ItemRequestService {
    private final UserService userService;
    private final ItemRequestRepository repository;

    @Transactional
    @Override
    public ItemRequest create(Long userId, ItemRequestCreateDto itemRequestDto) {
        User user = userService.get(userId);
        ItemRequest itemRequest = ItemRequest.builder()
                .description(itemRequestDto.getDescription())
                .created(LocalDateTime.now())
                .requestor(user)
                .build();
        ItemRequest newItemRequest = repository.save(itemRequest);
        log.info("Создан новый запрос предмета: {}", newItemRequest);
        return newItemRequest;
    }

    @Override
    public List<ItemRequest> getAllByUser(Long userId) {
        userService.get(userId);
        List<ItemRequest> itemRequestList = repository
                .findAllByRequestorIdOrderByCreatedAsc(userId);
        log.info("Получен список запросов предметов по requestorId размером: {}", itemRequestList.size());
        return itemRequestList;
    }

    @Override
    public List<ItemRequest> getAll(Long userId) {
        userService.get(userId);
        List<ItemRequest> itemRequestList = repository
                .findAllByRequestorIdIsNotOrderByCreatedAsc(userId);
        log.info("Получен список запросов предметов других пользователей размером: {}", itemRequestList.size());
        return itemRequestList;
    }

    @Override
    public ItemRequest get(Long requestId) {
        ItemRequest itemRequest = repository.findById(requestId).orElseThrow(() -> new NotFoundException("Запрос не найден"));
        log.info("Получена информация о запросе предмета: {}", itemRequest);
        return itemRequest;
    }
}
