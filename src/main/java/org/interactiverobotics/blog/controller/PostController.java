package org.interactiverobotics.blog.controller;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.form.PostForm;
import org.interactiverobotics.blog.model.Post;
import org.interactiverobotics.blog.service.PostService;
import org.interactiverobotics.blog.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/")
    public List<Post> getPosts() {
        return postService.getPosts().stream()
                .map(Post::copy) // required to get rid of MyBatis wrapper
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Post getById(@PathVariable("id") long id) {
        return Post.copy(postService.getById(id));
    }

    @GetMapping("/user/{id}")
    public List<Post> getUserPosts(@PathVariable("id") long userId) {
        return postService.getUserPosts(userService.getById(userId)).stream()
                .map(Post::copy)
                .collect(Collectors.toList());
    }

    @PostMapping("/user/{id}")
    public Post create(@PathVariable("id") long userId, @RequestBody PostForm postForm) {
        return Post.copy(postService.create(userService.getById(userId), postForm.getText()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") long id) {
        postService.delete(postService.getById(id));
        return ResponseEntity.ok().build();
    }
}
