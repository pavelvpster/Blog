package org.interactiverobotics.blog.service;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.exception.UpdateException;
import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.mapper.UserMapper;
import org.interactiverobotics.blog.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserMapper userMapper;

    /**
     * Returns all users.
     */
    @NotNull
    public List<User> getUsers() {
        return userMapper.findAll();
    }

    /**
     * Returns user by id.
     */
    @NotNull
    public User getById(long id) {
        return Optional.ofNullable(userMapper.findById(id))
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));
    }

    @NotNull
    public User getByIdWithPosts(long id) {
        return Optional.ofNullable(userMapper.findByIdWithPosts(id))
                .orElseThrow(() -> new UserNotFoundException("User not found by id: " + id));
    }

    /**
     * Returns user by name.
     */
    @NotNull
    public User getByName(@NotNull String name) {
        return Optional.ofNullable(userMapper.findByName(name))
                .orElseThrow(() -> new UserNotFoundException("User not found by name: " + name));
    }

    @NotNull
    public User create(@NotNull String name) {
        LOGGER.info("Create user. Name: {}", name);
        User user = User.builder()
                .name(name)
                .build();
        try {
            int n = userMapper.save(user);
            LOGGER.debug("Records inserted: {}", n);
        } catch (DataAccessException e) {
            LOGGER.warn("User insert error: ", e);
            throw new UpdateException("User insert error", e);
        }
        return user;
    }

    public void delete(@NotNull User user) {
        LOGGER.info("Delete user: {}", user);
        int n = userMapper.delete(user);
        if (n != 1) {
            LOGGER.warn("User was not deleted");
            throw new UpdateException("User was not deleted");
        }
    }
}
