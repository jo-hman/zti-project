package com.jochman.zti.post.service;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.model.response.PostsResponse;
import com.jochman.zti.post.repository.Comment;
import com.jochman.zti.post.repository.Post;
import com.jochman.zti.post.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;

    public void createPost(PostRequest postRequest, User user) {
        Post post = new Post(UUID.randomUUID().toString(), user.getId());
        post.setContent(postRequest.content());
        post.setTitle(postRequest.title());
        postsRepository.save(post);
    }

    public List<Post> getPosts() {
        return postsRepository.findAll();
    }

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
