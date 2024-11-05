package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.booking.dto.BookingCreateDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.booking.model.BookingStatus;
import ru.practicum.shareit.exception.ForbiddenException;
import ru.practicum.shareit.exception.NotAllowedException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.ItemService;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final ItemService itemService;

    @Override
    @Transactional
    public Booking create(Long bookerId, BookingCreateDto bookingDto) {
        User user = userService.get(bookerId);
        Item item = itemService.get(bookingDto.getItemId());

        if (!item.getAvailable()) {
            throw new NotAllowedException("Нельзя заказать бронь, товар не доступен");
        }

        Booking booking = Booking.builder()
                .startDate(bookingDto.getStart())
                .endDate(bookingDto.getEnd())
                .status(BookingStatus.WAITING)
                .item(item)
                .booker(user)
                .build();
        Booking newBooking = bookingRepository.save(booking);
        log.info("Создана бронь: {}", newBooking);
        return newBooking;
    }

    @Override
    @Transactional
    public Booking updateStatus(Long userId, Long bookingId, Boolean approved) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new NotFoundException("Бронь не найдена" + bookingId));
        if (!userId.equals(booking.getItem().getOwner().getId())) {
            throw new ForbiddenException("Нельзя изменить статус брони");
        }
        if (approved) {
            booking.setStatus(BookingStatus.APPROVED);
        } else {
            booking.setStatus(BookingStatus.REJECTED);
        }
        Booking newBooking = bookingRepository.save(booking);
        log.info("Обновлена бронь: {}", newBooking);
        return newBooking;
    }

    @Override
    public Booking get(Long userId, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(()-> new NotFoundException("Бронь не найдена" + bookingId));
        if (userId.equals(booking.getItem().getOwner().getId()) || userId.equals(booking.getBooker().getId())) {
            log.info("Получена бронь: {}", booking);
            return booking;
        }
        throw new ForbiddenException("Отказано в доступе");
    }

    @Override
    public List<Booking> findAll(Long bookerId, String state) {
        userService.get(bookerId);

        BookingState bookingState = BookingState.valueOf(state);

        List<Booking> bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllByBookerId(bookerId);
            case CURRENT -> bookingRepository.findCurrentByBookerId(bookerId);
            case PAST -> bookingRepository.findPastByBookerId(bookerId);
            case FUTURE -> bookingRepository.findFutureByBookerId(bookerId);
            case WAITING -> bookingRepository.findWaitingByBookerId(bookerId);
            case REJECTED -> bookingRepository.findRejectedByBookerId(bookerId);
        };
        log.info("Получен список броней по bookerId размером: {}", bookings.size());
        return bookings;
    }

    @Override
    public List<Booking> findAllOwner(Long ownerId, String state) {
        userService.get(ownerId);

        BookingState bookingState = BookingState.valueOf(state);

        List<Booking> bookings = switch (bookingState) {
            case ALL -> bookingRepository.findAllByOwnerId(ownerId);
            case CURRENT -> bookingRepository.findCurrentByOwnerId(ownerId);
            case PAST -> bookingRepository.findPastByOwnerId(ownerId);
            case FUTURE -> bookingRepository.findFutureByOwnerId(ownerId);
            case WAITING -> bookingRepository.findWaitingByOwnerId(ownerId);
            case REJECTED -> bookingRepository.findRejectedByOwnerId(ownerId);
        };
        log.info("Получен список броней по ownerId размером: {}", bookings.size());
        return bookings;
    }
}
