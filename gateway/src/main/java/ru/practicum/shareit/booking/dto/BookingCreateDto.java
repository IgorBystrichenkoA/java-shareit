package ru.practicum.shareit.booking.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingCreateDto {
    @NotNull
    @FutureOrPresent(message = "Начало не может быть раньше текущего дня")
    private LocalDateTime start;
    @NotNull
    @FutureOrPresent(message = "Конец не может быть раньше текущего дня")
    private LocalDateTime end;
    @NotNull
    private Long itemId;

    @AssertTrue(message = "Конец должен быть позже начала")
    public boolean isValidEnd() {
        return end.isAfter(start);
    }
}
