package ru.practicum.shareit.usertests;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.practicum.shareit.user.UserMapper;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    private UserCreateDto userCreateDto;

    @BeforeEach
    void init() {
        userCreateDto = UserCreateDto.builder()
                      .name("name")
                      .email("email@email.com")
                      .build();
    }

    @Test
    void getTest() {
        UserDto userFromService = UserMapper.toUserDto(userService.get(1L));
        assertEquals(getFirstTestUserDto(), userFromService);
    }

    @Test
    void createTest() {
        UserDto userFromService = UserMapper.toUserDto(userService.create(userCreateDto));
        assertEquals(getCreatedTestUserDto(), userFromService);
    }

    @Test
    void updateTest() {
        UserDto userDto = UserDto
                .builder()
                .name("update name")
                .email("update@email.com")
                .build();

        UserDto userBeforeUpdate = UserMapper.toUserDto(userService.get(1L));;
        UserDto userAfterService = UserMapper.toUserDto(userService.update(1L, userDto));
        userBeforeUpdate.setName("update name");
        userBeforeUpdate.setEmail("update@email.com");

        assertEquals(userBeforeUpdate, userAfterService);
    }

    @Test
    void deleteTest() {
        assertDoesNotThrow(() -> userService.deleteUserById(1L));
    }

    private UserDto getFirstTestUserDto() {
        return UserDto
                .builder()
                .id(1L)
                .name("Андрей")
                .email("an@gmail.com")
                .build();
    }

    private UserDto getCreatedTestUserDto() {
        return UserDto
                .builder()
                .id(4L)
                .name(userCreateDto.getName())
                .email(userCreateDto.getEmail())
                .build();
    }
}
