package ru.practicum.shareit.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.Item;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    private String text;
    private String authorName;
    private LocalDateTime created;
    private Item item;
}
