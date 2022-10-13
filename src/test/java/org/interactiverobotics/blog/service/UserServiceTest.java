package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.mapper.UserMapper;
import org.interactiverobotics.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUsers_returnsUsers() {
        User user = User.builder()
                .id(1L)
                .name("alice")
                .build();
        when(userMapper.findAll()).thenReturn(List.of(user));

        List<User> actual = userService.getUsers();

        assertFalse(actual.isEmpty());
        assertEquals(user, actual.get(0));
    }

    @Test
    public void getById_whenUserExists_returnsUser() {
        long id = 1L;

        User user = User.builder()
                .id(id)
                .name("alice")
                .build();
        when(userMapper.findById(eq(id))).thenReturn(user);

        User actual = userService.getById(id);

        assertEquals(user, actual);
    }

    @Test
    public void getById_whenUserDoesNotExist_throwsException() {
        when(userMapper.findById(anyLong())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getById(-1L));
    }

    @Test
    public void getByName_whenUserExists_returnsUser() {
        String name = "alice";

        User user = User.builder()
                .id(1L)
                .name(name)
                .build();
        when(userMapper.findByName(eq(name))).thenReturn(user);

        User actual = userService.getByName(name);

        assertEquals(user, actual);
    }

    @Test
    public void getByName_whenUserDoesNotExist_throwsException() {
        when(userMapper.findByName(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getByName("clark"));
    }
}