package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository {
    Optional<User> get(Long id);

    User create(UserCreateDto user);

    User update(User newUser);

    void deleteUserById(Long userId);

    List<User> findAll();
}
