package ru.practicum.shareit.usertests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.practicum.shareit.user.UserController;
import ru.practicum.shareit.user.UserService;
import ru.practicum.shareit.user.dto.UserCreateDto;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc
public class UserControllerTests {
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mvc;

    private UserCreateDto userCreateDto;

    private User user;

    private UserDto userDto;

    private Long id = 1L;

    @BeforeEach
    void init() {
        userCreateDto = UserCreateDto
                .builder()
                .name("user name")
                .email("user@email.com")
                .build();

        user = User
                .builder()
                .id(id)
                .name("user name")
                .email("user@email.com")
                .build();

        userDto = UserDto
                .builder()
                .id(id)
                .name("user name")
                .email("user@email.com")
                .build();
    }

    @Test
    void getTest() throws Exception {
        when(userService.get(anyLong()))
                .thenReturn(user);

        mvc.perform(get("/users/1")
                        .header("X-Sharer-User-Id", id)
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(userDto)));
        verify(userService, times(1)).get(id);
    }

    @Test
    void createTest() throws Exception {
        doReturn(user).when(userService).create(any());

        mvc.perform(post("/users")
                        .content(mapper.writeValueAsString(userCreateDto))
                        .contentType(MediaType.APPLICATION_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json(mapper.writeValueAsString(userDto)));
        verify(userService, times(1)).create(userCreateDto);
    }

    @Test
    void updateTest() throws Exception {
        when(userService.update(anyLong(), any()))
                .thenReturn(user);

        mvc.perform(patch("/users/1")
                        .content(mapper.writeValueAsString(userDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(userDto)));
        verify(userService, times(1)).update(id, userDto);
    }

    @Test
    void deleteTest() throws Exception {
        mvc.perform(delete("/users/1"))
                .andExpect(status().isOk());
        verify(userService, times(1)).deleteUserById(id);
    }
}
