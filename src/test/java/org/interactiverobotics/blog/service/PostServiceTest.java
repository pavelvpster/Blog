package org.interactiverobotics.blog.service;

import org.interactiverobotics.blog.exception.PostNotFoundException;
import org.interactiverobotics.blog.mapper.PostMapper;
import org.interactiverobotics.blog.model.Post;
import org.interactiverobotics.blog.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostMapper postMapper;

    @InjectMocks
    private PostService postService;

    @Test
    public void getPosts_returnsPosts() {
        Post post = Post.builder()
                .id(1L)
                .user(makeUser())
                .text("abc")
                .build();
        when(postMapper.finaAll()).thenReturn(List.of(post));

        List<Post> actual = postService.getPosts();

        assertFalse(actual.isEmpty());
        assertEquals(post, actual.get(0));
    }

    @Test
    public void getById_whenPostExists_returnsPost() {
        long id = 1L;

        Post post = Post.builder()
                .id(id)
                .user(makeUser())
                .text("abc")
                .build();
        when(postMapper.findById(eq(id))).thenReturn(post);

        Post actual = postService.getById(id);

        assertEquals(post, actual);
    }

    @Test
    public void getById_whenPostDoesNotExist_throwsException() {
        when(postMapper.findById(anyLong())).thenReturn(null);

        assertThrows(PostNotFoundException.class, () -> postService.getById(-1L));
    }

    @Test
    public void getUserPosts_whenPostsExist_returnsPosts() {
        User user = makeUser();
        Post post = Post.builder()
                .id(2L)
                .user(user)
                .text("abc")
                .build();
        when(postMapper.findByUser(eq(user.getId()))).thenReturn(List.of(post));

        List<Post> actual = postService.getUserPosts(user);

        assertFalse(actual.isEmpty());
        assertEquals(post, actual.get(0));
    }

    private User makeUser() {
        return User.builder()
                .id(1L)
                .name("user")
                .build();
    }
}