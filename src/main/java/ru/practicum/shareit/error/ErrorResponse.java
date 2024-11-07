package ru.practicum.shareit.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {
    private Integer status;
    private String message;
    private String error;
}