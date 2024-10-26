package ru.practicum.shareit.user;

import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<Long, User> users = new ConcurrentHashMap<>();
    private long seq = 0L;

    @Override
    public User get(Long id) {
        return users.get(id);
    }

    @Override
    public User create(UserCreateDto user) {
        if (users.values().stream().anyMatch(u -> u.getEmail().equals(user.getEmail()))) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        User newUser = new User(++seq, user.getName(), user.getEmail());
        users.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public User update(User user) {
        User userToUpdate = users.get(user.getId());
        if (userToUpdate == null) {
            throw new NotFoundException("Пользователь не найден");
        }
        if (user.getName() != null) {
            userToUpdate.setName(user.getName());
        }
        if (user.getEmail() != null) {
            userToUpdate.setEmail(user.getEmail());
        }
        return userToUpdate;
    }

    @Override
    public void deleteUserById(Long userId) {
        users.remove(userId);
    }
}
