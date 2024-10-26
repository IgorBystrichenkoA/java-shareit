package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public UserDto get(@PathVariable Long id) {
        User user = userService.get(id);
        log.info("Получен пользователь: {}", user);
        return UserMapper.toUserDto(user);
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody UserCreateDto user) {
        User newUser = userService.create(user);
        log.info("Создан пользователь: {}", newUser);
        return UserMapper.toUserDto(newUser);
    }

    @PatchMapping("/{userId}")
    public UserDto update(@PathVariable Long userId,
                          @Valid @RequestBody UserDto user) {
        User updatedUser = userService.update(new User(userId, user.getName(), user.getEmail()));
        log.info("Изменен пользователь: {}", updatedUser);
        return UserMapper.toUserDto(updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
        log.info("Пользователь с id = \"{}\" удален", userId);
    }
}
