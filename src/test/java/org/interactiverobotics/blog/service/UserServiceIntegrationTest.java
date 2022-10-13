package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUsers_returnsUsers() {
        List<User> actual = userService.getUsers();

        assertFalse(actual.isEmpty());
        User alice = actual.stream()
                .filter(user -> "alice".equals(user.getName()))
                .findAny()
                .get();
        assertNotNull(alice);

        User bob = actual.stream()
                .filter(user -> "bob".equals(user.getName()))
                .findAny()
                .get();
        assertNotNull(bob);
    }

    @Test
    public void getByName_whenAliceRequested_returnsUser() {
        User actual = userService.getByName("alice");

        assertNotNull(actual);
    }

    @Test
    public void getByName_whenClarkRequested_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getByName("clark"));
    }
}
