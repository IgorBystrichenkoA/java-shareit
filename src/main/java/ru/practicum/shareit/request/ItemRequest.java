package ru.practicum.shareit.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * TODO Sprint add-item-requests.
 */
@Data
@EqualsAndHashCode(of = "id")
public class ItemRequest {
    private Long id;
    private Long itemId;
    private String name;
    private String description;
}
