package ru.practicum.shareit.request;

import lombok.Data;

/**
 * TODO Sprint add-item-requests.
 */
@Data
public class ItemRequest {
    private Long id;
    private Long itemId;
    private String name;
    private String description;
}
