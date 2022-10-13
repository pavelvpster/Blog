package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

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
        Optional<User> alice = actual.stream()
                .filter(user -> "alice".equals(user.getName()))
                .findAny();
        assertTrue(alice.isPresent());
        assertNull(alice.get().getPosts());

        Optional<User> bob = actual.stream()
                .filter(user -> "bob".equals(user.getName()))
                .findAny();
        assertTrue(bob.isPresent());
        assertNull(bob.get().getPosts());
    }

    @Test
    public void getByName_whenAliceRequested_returnsUser() {
        userService.getByName("alice");
    }

    @Test
    public void getByName_whenClarkRequested_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getByName("clark"));
    }

    @Test
    public void getByIdWithPosts_returnsPosts() {
        User alice = userService.getByName("alice");

        User actual = userService.getByIdWithPosts(alice.getId());

        assertNotNull(actual.getPosts());
        assertEquals("Hello, world!", actual.getPosts().get(0).getText());
    }
}
