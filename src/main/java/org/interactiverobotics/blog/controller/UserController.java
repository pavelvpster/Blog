package org.interactiverobotics.blog.controller;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.form.UserForm;
import org.interactiverobotics.blog.model.User;
import org.interactiverobotics.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable("id") long id) {
        return userService.getById(id);
    }

    @GetMapping("/find")
    public User getByName(@RequestParam("name") String name) {
        return userService.getByName(name);
    }

    @PostMapping("/")
    public User create(@RequestBody UserForm userForm) {
        return userService.create(userForm.getName());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        userService.delete(userService.getById(id));
        return ResponseEntity.ok().build();
    }
}
