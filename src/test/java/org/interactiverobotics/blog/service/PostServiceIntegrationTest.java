package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.model.Post;
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
public class PostServiceIntegrationTest {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Test
    public void getPosts_returnsPosts() {
        List<Post> actual = postService.getPosts();

        assertFalse(actual.isEmpty());
    }

    @Test
    public void getUserPosts_returnsUserPosts() {
        User user = userService.getByName("alice");
        List<Post> actual = postService.getUserPosts(user);

        assertFalse(actual.isEmpty());
        assertEquals(user, actual.get(0).getUser());
        assertEquals("Hello, world!", actual.get(0).getText());
    }

    @Test
    public void create_createsPost() {
        User user = userService.getByName("alice");
        Post actual = postService.create(user, "TEST");

        assertTrue(actual.getId() > 0);

        postService.delete(actual);
    }

    @Test
    public void delete_deletesPost() {
        String text = "TEST";

        User user = userService.getByName("alice");
        Post post = postService.create(user, text);

        postService.delete(post);

        List<Post> actual = postService.getUserPosts(user);

        assertTrue(actual.stream()
                .map(Post::getText)
                .noneMatch(t -> t.equals(text)));
    }
}
