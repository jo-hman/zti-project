package com.jochman.zti.post.model.response;

import com.jochman.zti.post.repository.Comment;

import java.util.List;

/**
 * Represents a response object for a post, including its comments.
 */
public record PostResponse(String postId, String userId, String title, String content, List<Comment> comments) {

}
