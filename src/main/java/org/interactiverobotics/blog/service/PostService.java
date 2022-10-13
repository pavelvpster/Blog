package org.interactiverobotics.blog.service;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.exception.PostNotFoundException;
import org.interactiverobotics.blog.mapper.PostMapper;
import org.interactiverobotics.blog.model.Post;
import org.interactiverobotics.blog.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private final PostMapper postMapper;

    /**
     * Returns all posts.
     */
    @NotNull
    public List<Post> getPosts() {
        return postMapper.finaAll();
    }

    /**
     * Returns post by id.
     */
    @NotNull
    public Post getById(long id) {
        return Optional.ofNullable(postMapper.findById(id))
                .orElseThrow(() -> new PostNotFoundException("Post not found by id: " + id));
    }

    /**
     * Returns posts for given user.
     */
    @NotNull
    public List<Post> getUserPosts(@NotNull User user) {
        return postMapper.findByUser(user.getId());
    }
}
