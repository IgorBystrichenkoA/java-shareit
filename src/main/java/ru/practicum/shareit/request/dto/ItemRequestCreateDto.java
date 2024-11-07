package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.practicum.shareit.user.model.User;

@Data
public class ItemRequestCreateDto {
    @NotBlank
    private String description;
    private User requestor;
}
