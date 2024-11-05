package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.BookingMapper;
import ru.practicum.shareit.booking.BookingService;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.booking.model.BookingState;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Comment;
import ru.practicum.shareit.item.model.Item;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemMapper {
    private final BookingService bookingService;
    private final ItemService itemService;

    public ItemDto toItemDto(Item item, Long userId) {
        BookingDto lastBookingDto = null;
        BookingDto futureBookingDto = null;

        if (item.getOwner().getId().equals(userId)) {
            List<Booking> lastBookings = bookingService.findAllOwner(item.getOwner().getId(), BookingState.CURRENT.name());
            if (lastBookings.isEmpty()) {
                lastBookings = bookingService.findAllOwner(item.getOwner().getId(), BookingState.PAST.name());
            }
            if (!lastBookings.isEmpty()) {
                lastBookingDto = BookingMapper.toBookingDto(lastBookings.getLast());
            }
            List<Booking> futureBookings = bookingService.findAllOwner(item.getOwner().getId(), BookingState.FUTURE.name());
            if (!futureBookings.isEmpty()) {
                futureBookingDto = BookingMapper.toBookingDto(futureBookings.getLast());
            }
        }

        List<Comment> comments = itemService.findAllByItemId(item.getId());

        return new ItemDto(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getAvailable(),
                item.getRequest() != null ? item.getRequest().getId() : null,
                lastBookingDto,
                comments.isEmpty() ? null : comments.stream().map(CommentMapper::toCommentDto).toList(),
                futureBookingDto
        );
    }
}
