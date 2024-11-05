package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.model.User;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User get(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        log.info("Получен пользователь: {}", user);
        return user;
    }

    @Override
    @Transactional
    public User create(UserCreateDto userDto) {
        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
        User newUser = repository.save(user);
        log.info("Создан новый пользователь: {}", newUser);
        return newUser;
    }

    @Override
    @Transactional
    public User update(User user) {
        User dbUser = get(user.getId());
        if (user.getName() != null) {
            dbUser.setName(user.getName());
        }
        if (user.getEmail() != null) {
            dbUser.setEmail(user.getEmail());
        }
        User updatedUser = repository.save(dbUser);
        log.info("Обновлен пользователь: {}", updatedUser);
        return updatedUser;
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        repository.deleteById(userId);
        log.info("Удален пользователь: {}", userId);
    }
}
