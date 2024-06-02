package com.jochman.zti.post.service;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.repository.Comment;
import com.jochman.zti.post.repository.Post;
import com.jochman.zti.post.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class for handling operations related to posts.
 */
@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    /**
     * Creates a new post.
     * @param postRequest the request object containing post details
     * @param user the user creating the post
     */
    public void createPost(PostRequest postRequest, User user) {
        Post post = new Post(UUID.randomUUID().toString(), user.getId());
        post.setContent(postRequest.content());
        post.setTitle(postRequest.title());
        postsRepository.save(post);
    }

    /**
     * Retrieves all posts.
     * @return a list of all posts
     */
    public List<Post> getPosts() {
        return postsRepository.findAll();
    }

    /**
     * Creates a new comment on a post.
     * @param postId the ID of the post to comment on
     * @param userId the ID of the user creating the comment
     * @param commentRequest the request object containing the comment details
     * @return true if the comment is created successfully, otherwise false
     */
    public boolean createComment(String postId, String userId, CommentRequest commentRequest) {
        return postsRepository.findById(postId)
                .map(post -> {
                    Comment comment = new Comment();
                    comment.setComment(commentRequest.comment());
                    post.addComment(comment);
                    postsRepository.save(post);
                    return true;
                })
                .orElse(false);
    }
}
