package com.jochman.zti.post;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.controller.PostsController;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.model.response.PostResponse;
import com.jochman.zti.post.repository.Comment;
import com.jochman.zti.post.repository.Post;
import com.jochman.zti.post.service.PostsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostsControllerTest {

    @Mock
    private PostsService postsService;

    @InjectMocks
    private PostsController postsController;

    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    public void setUp() {
        user = new User(UUID.randomUUID().toString(), "test@example.com", "password123");
        post = new Post(UUID.randomUUID().toString(), user.getId());
        post.setTitle("Test Title");
        post.setContent("Test Content");
        comment = new Comment();
        comment.setComment("Test Comment");
    }

    @Test
    public void testCreatePost_Success() {
        PostRequest postRequest = new PostRequest("Test Title", "Test Content");

        doNothing().when(postsService).createPost(any(PostRequest.class), any(User.class));

        ResponseEntity<Void> response = postsController.createPost(postRequest, user);

        assertEquals(200, response.getStatusCodeValue());
        verify(postsService, times(1)).createPost(any(PostRequest.class), any(User.class));
    }

    @Test
    public void testGetPosts_Success() {
        List<Post> posts = List.of(post);

        when(postsService.getPosts()).thenReturn(posts);

        ResponseEntity<List<PostResponse>> response = postsController.getPosts();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Test Title", response.getBody().get(0).title());
        assertEquals("Test Content", response.getBody().get(0).content());
        verify(postsService, times(1)).getPosts();
    }

    @Test
    public void testCreateComment_Success() {
        CommentRequest commentRequest = new CommentRequest("Test Comment");

        when(postsService.createComment(anyString(), anyString(), any(CommentRequest.class))).thenReturn(true);

        ResponseEntity<Void> response = postsController.createComment(post.getId(), commentRequest, user);

        assertEquals(200, response.getStatusCodeValue());
        verify(postsService, times(1)).createComment(anyString(), anyString(), any(CommentRequest.class));
    }

    @Test
    public void testCreateComment_Failure() {
        CommentRequest commentRequest = new CommentRequest("Test Comment");

        when(postsService.createComment(anyString(), anyString(), any(CommentRequest.class))).thenReturn(false);

        ResponseEntity<Void> response = postsController.createComment(post.getId(), commentRequest, user);

        assertEquals(400, response.getStatusCodeValue());
        verify(postsService, times(1)).createComment(anyString(), anyString(), any(CommentRequest.class));
    }
}
