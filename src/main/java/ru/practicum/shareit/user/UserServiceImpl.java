package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User get(Long id) {
        return repository.get(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
    }

    @Override
    public User create(UserCreateDto user) {
        if (containsEmail(user.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }
        return repository.create(user);
    }

    private boolean containsEmail(String email) {
        List<User> users = repository.findAll();
        return users.stream().anyMatch(u -> u.getEmail().equals(email));
    }
    @Override
    public User update(User user) {
        User dbUser = repository.get(user.getId()).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        if (user.getName() != null) {
            dbUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            if (containsEmail(user.getEmail())) {
                throw new IllegalArgumentException("Пользователь с таким email уже существует");
            }
            dbUser.setEmail(user.getEmail());
        }
        return repository.update(dbUser);
    }

    @Override
    public void deleteUserById(Long userId) {
        repository.deleteUserById(userId);
    }
}
