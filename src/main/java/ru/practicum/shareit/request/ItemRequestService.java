package ru.practicum.shareit.request;

import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;

public interface ItemRequestService {
    User get(Long id);

    User create(UserCreateDto user);

    User update(User newUser);

    void deleteUserById(Long userId);
}
