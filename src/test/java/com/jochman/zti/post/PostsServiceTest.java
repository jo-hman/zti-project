package com.jochman.zti.post;

import com.jochman.zti.auth.repository.User;
import com.jochman.zti.post.model.request.CommentRequest;
import com.jochman.zti.post.model.request.PostRequest;
import com.jochman.zti.post.repository.Post;
import com.jochman.zti.post.repository.PostsRepository;
import com.jochman.zti.post.service.PostsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostsServiceTest {

    @Mock
    private PostsRepository postsRepository;

    @InjectMocks
    private PostsService postsService;

    @Test
    public void testCreatePost() {
        // given
        User user = new User(UUID.randomUUID().toString(), "user@example.com", "password123");
        PostRequest postRequest = new PostRequest("Test Title", "Test Content");

        // when
        postsService.createPost(postRequest, user);

        // then
        verify(postsRepository, times(1)).save(any(Post.class));
    }

    @Test
    public void testGetPosts() {
        // given
        List<Post> posts = List.of(
                new Post(UUID.randomUUID().toString(), "user1"),
                new Post(UUID.randomUUID().toString(), "user2")
        );

        when(postsRepository.findAll()).thenReturn(posts);

        // when
        List<Post> result = postsService.getPosts();

        // then
        assertEquals(2, result.size());
        verify(postsRepository, times(1)).findAll();
    }

    @Test
    public void testCreateComment_PostExists() {
        // given
        String postId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        CommentRequest commentRequest = new CommentRequest("Test Comment");
        Post post = new Post(postId, userId);

        when(postsRepository.findById(postId)).thenReturn(Optional.of(post));

        // when
        boolean result = postsService.createComment(postId, userId, commentRequest);

        // then
        assertTrue(result);
        assertEquals(1, post.getComments().size());
        verify(postsRepository, times(1)).save(post);
    }

    @Test
    public void testCreateComment_PostDoesNotExist() {
        // given
        String postId = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        CommentRequest commentRequest = new CommentRequest("Test Comment");

        when(postsRepository.findById(postId)).thenReturn(Optional.empty());

        // when
        boolean result = postsService.createComment(postId, userId, commentRequest);

        // then
        assertFalse(result);
        verify(postsRepository, never()).save(any(Post.class));
    }
}
