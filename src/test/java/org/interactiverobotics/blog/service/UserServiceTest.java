package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.UpdateException;
import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.mapper.UserMapper;
import org.interactiverobotics.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final String ALICE = "alice";

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    @Test
    public void getUsers_returnsUsers() {
        User user = User.builder()
                .id(1L)
                .name(ALICE)
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
                .name(ALICE)
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
        User user = User.builder()
                .id(1L)
                .name(ALICE)
                .build();
        when(userMapper.findByName(eq(ALICE))).thenReturn(user);

        User actual = userService.getByName(ALICE);

        assertEquals(user, actual);
    }

    @Test
    public void getByName_whenUserDoesNotExist_throwsException() {
        when(userMapper.findByName(anyString())).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.getByName("clark"));
    }

    @Test
    public void create_createsUser() {
        when(userMapper.save(any(User.class))).thenReturn(1);

        userService.create(ALICE);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).save(captor.capture());
        User actual = captor.getValue();

        assertEquals(ALICE, actual.getName());
    }

    @Test
    public void create_whenDbThrowsException_throwsException() {
        when(userMapper.save(any(User.class))).thenThrow(mock(DataAccessException.class));

        assertThrows(UpdateException.class, () -> userService.create(ALICE));
    }

    @Test
    public void delete_deletesUser() {
        User user = User.builder()
                .id(1L)
                .name(ALICE)
                .build();

        when(userMapper.delete(eq(user))).thenReturn(1);

        userService.delete(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userMapper).delete(captor.capture());
        User actual = captor.getValue();

        assertEquals(user, actual);
    }

    @Test
    public void delete_whenNoRecordsDeleted_throwsException() {
        when(userMapper.delete(any(User.class))).thenReturn(0);

        assertThrows(UpdateException.class, () -> userService.delete(new User()));
    }
}