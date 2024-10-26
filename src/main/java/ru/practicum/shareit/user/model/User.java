package ru.practicum.shareit.user.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    private Long id;
    private String name;
    @Email
    private String email;
}
