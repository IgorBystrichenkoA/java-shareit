package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;


public interface UserService {
    User get(Long id);

    User create(UserCreateDto user);

    User update(Long userId, UserDto newUser);

    void deleteUserById(Long userId);
}
