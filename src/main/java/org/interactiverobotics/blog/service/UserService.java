package org.interactiverobotics.blog.service;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.exception.UserNotFoundException;
import org.interactiverobotics.blog.mapper.UserMapper;
import org.interactiverobotics.blog.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
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
}
