package ru.practicum.shareit.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.exception.NotAllowedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.dto.*;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.CommentRepository;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.ItemRequest;
import ru.practicum.shareit.request.ItemRequestService;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final BookingService bookingService;
    private final ItemRequestService itemRequestService;

    public ItemServiceImpl(ItemRepository itemRepository, CommentRepository commentRepository, UserService userService,
                           @Lazy BookingService bookingService, @Lazy ItemRequestService itemRequestService) {
        this.itemRepository = itemRepository;
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.bookingService = bookingService;
        this.itemRequestService = itemRequestService;
    }

    @Override
    public Item get(Long id) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new NotFoundException("Предмет не найден"));
        log.info("Получен предмет: {}", item);
        return item;
    }

    @Override
    @Transactional
    public Item create(Long ownerId, ItemCreateDto itemCreateDto) {
        User user = userService.get(ownerId);

        Item item = Item.builder()
                .name(itemCreateDto.getName())
                .description(itemCreateDto.getDescription())
                .available(itemCreateDto.getAvailable())
                .owner(user)
                .build();
        if (itemCreateDto.getRequestId() != null) {
            ItemRequest request = itemRequestService.get(itemCreateDto.getRequestId());
            item.setRequest(request);
        }
        Item newItem = itemRepository.save(item);
        log.info("Создан предмет: {}", newItem);
        return newItem;
    }

    @Override
    @Transactional
    public Item update(Long itemId, Long ownerId, ItemUpdateDto itemUpdateDto) {
        Item dbItem = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет не найден"));
        User user = userService.get(ownerId);
        if (!user.equals(dbItem.getOwner())) {
            throw new NotFoundException("Предмет не найден");
        }
        if (itemUpdateDto.getName() != null) {
            dbItem.setName(itemUpdateDto.getName());
        }
        if (itemUpdateDto.getDescription() != null) {
            dbItem.setDescription(itemUpdateDto.getDescription());
        }
        if (itemUpdateDto.getAvailable() != null) {
            dbItem.setAvailable(itemUpdateDto.getAvailable());
        }
        Item item = itemRepository.save(dbItem);
        log.info("Обновлен предмет: {}", item);
        return item;
    }

    @Override
    public List<Item> getByUserId(Long userId) {
        List<Item> items = itemRepository.findAllByOwnerId(userId);
        log.info("Получен список предметов пользователя длиной: {}", items.size());
        return items;
    }

    @Override
    public List<Item> search(String text) {
        List<Item> items = itemRepository.search(text);
        log.info("Получен список предметов по тексту: {}", items.size());
        return items;
    }

    @Override
    @Transactional
    public Comment createComment(Long userId, Long itemId, CommentCreateDto commentCreateDto) {
        User user = userService.get(userId);
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotFoundException("Предмет не найден"));
        List<Booking> booking = bookingService.findAll(userId, BookingState.PAST.name());
        if (booking.isEmpty()) {
            throw new NotAllowedException("Вы не можете оставить комментарий");
        }
        Comment comment = Comment.builder()
                .text(commentCreateDto.getText())
                .item(item)
                .author(user)
                .created(LocalDateTime.now())
                .build();
        Comment newComment = commentRepository.save(comment);
        log.info("Cоздан комментарий = {}", newComment);
        return newComment;
    }

    @Override
    public List<Comment> findAllByItemId(Long itemId) {
        return commentRepository.findAllByItemId(itemId);
    }
}
