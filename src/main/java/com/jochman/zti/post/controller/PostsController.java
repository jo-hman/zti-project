package com.jochman.zti.post.controller;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.model.response.PostResponse;
import com.jochman.zti.post.model.response.PostsResponse;
import com.jochman.zti.post.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("posts")
@RestController
@RequiredArgsConstructor
public class PostsController {

    private final PostsService postsService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest, @AuthenticationPrincipal User user) {
        postsService.createPost(postRequest, user);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<PostsResponse> getPosts() {
        return ResponseEntity.ok(new PostsResponse(postsService.getPosts().stream()
                .map(post ->  new PostResponse(post.getId(), post.getUserId(), post.getTitle(), post.getContent(), post.getComments())).toList()));
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<Void> createComment(@PathVariable("id") String postId, @RequestBody CommentRequest commentRequest, @AuthenticationPrincipal User user) {
        return postsService.createComment(postId, user.getId(), commentRequest) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
