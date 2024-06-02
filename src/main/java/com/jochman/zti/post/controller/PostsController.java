package com.jochman.zti.post.controller;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.model.response.PostResponse;
import com.jochman.zti.post.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling posts-related requests.
 */
@RequestMapping("posts")
@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    /**
     * Endpoint for creating a new post.
     * @param postRequest the post request object containing post details
     * @param user the authenticated user creating the post
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal User user) {
        postsService.createPost(postRequest, user);
        return ResponseEntity.ok().build();
    }

    /**
     * Endpoint for retrieving all posts.
     * @return ResponseEntity containing a list of post responses
     */
    @GetMapping
    public ResponseEntity<List<PostResponse>> getPosts() {
        return ResponseEntity.ok(postsService.getPosts().stream()
                .map(post ->  new PostResponse(post.getId(), post.getUserId(), post.getTitle(), post.getContent(), post.getComments())).toList());
    }

    /**
     * Endpoint for creating a new comment on a post.
     * @param postId the ID of the post to comment on
     * @param commentRequest the comment request object containing comment details
     * @param user the authenticated user creating the comment
     * @return ResponseEntity indicating success or failure
     */
    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(@PathVariable("id") String postId, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal User user) {
        return postsService.createComment(postId, user.getId(), commentRequest) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }
}
