package com.jochman.zti.post.service;

import com.jochman.zti.auth.service.JwtService;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.repository.Post;
import com.jochman.zti.post.repository.PostsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final JwtService jwtService;

    public void createPost(PostRequest postRequest) {
        postsRepository.save(new Post(UUID.randomUUID().toString(), jwtService.));
    }
}
