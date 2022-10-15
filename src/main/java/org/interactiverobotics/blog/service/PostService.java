package org.interactiverobotics.blog.service;

import lombok.AllArgsConstructor;
import org.interactiverobotics.blog.exception.PostNotFoundException;
import org.interactiverobotics.blog.exception.UpdateException;
import org.interactiverobotics.blog.mapper.PostMapper;
import org.interactiverobotics.blog.model.Post;
import org.interactiverobotics.blog.model.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PostService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

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

    @NotNull
    public Post create(@NotNull User user, @NotNull String text) {
        LOGGER.info("Create post. User: {} Text: '{}'", user, text);
        Post post = Post.builder()
                .user(user)
                .text(text)
                .build();
        int n = postMapper.save(post);
        if (n != 1) {
            LOGGER.warn("Post was not inserted");
            throw new UpdateException("Post insert error");
        }
        return post;
    }

    public void delete(@NotNull Post post) {
        LOGGER.info("Delete post: {}", post);
        postMapper.delete(post);
    }
}
