package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.UpdateException;
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
    private static final String ALICE = "alice";
    private static final String BOB = "bob";
    private static final String CLARK = "clark";

    @Autowired
    private UserService userService;

    @Test
    public void getUsers_returnsUsers() {
        List<User> actual = userService.getUsers();

        assertFalse(actual.isEmpty());
        Optional<User> alice = actual.stream()
                .filter(user -> ALICE.equals(user.getName()))
                .findAny();
        assertTrue(alice.isPresent());
        assertNull(alice.get().getPosts());

        Optional<User> bob = actual.stream()
                .filter(user -> BOB.equals(user.getName()))
                .findAny();
        assertTrue(bob.isPresent());
        assertNull(bob.get().getPosts());
    }

    @Test
    public void getByName_whenAliceRequested_returnsUser() {
        userService.getByName(ALICE);
    }

    @Test
    public void getByName_whenClarkRequested_throwsException() {
        assertThrows(UserNotFoundException.class, () -> userService.getByName(CLARK));
    }

    @Test
    public void getByIdWithPosts_returnsPosts() {
        User alice = userService.getByName(ALICE);

        User actual = userService.getByIdWithPosts(alice.getId());

        assertNotNull(actual.getPosts());
        assertEquals("Hello, world!", actual.getPosts().get(0).getText());
    }

    @Test
    public void create_createsUser() {
        User actual = userService.create(CLARK);

        assertTrue(actual.getId() > 0);

        userService.delete(actual);
    }

    @Test
    public void create_whenUserWithGivenNameAlreadyExists_throwsException() {
        User clark = userService.create(CLARK);

        assertThrows(UpdateException.class, () -> userService.create(CLARK));

        userService.delete(clark);
    }

    @Test
    public void delete_deletesUser() {
        User clark = userService.create(CLARK);

        userService.delete(clark);

        assertTrue(userService.getUsers().stream()
                .map(User::getName)
                .noneMatch(name -> CLARK.equals(name)));
    }
}
