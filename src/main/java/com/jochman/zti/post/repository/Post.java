package com.jochman.zti.post.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

/**
 * Represents a post entity in the database.
 */
@Document
@Data
public class Post {

    @Id
    private final String id;

    @Field
    private final String userId;

    @Field
    private String title;

    @Field
    private String content;

    @Field
    private List<Comment> comments;

    /**
     * Adds a comment to the post.
     * @param comment the comment to add
     */
    public void addComment(Comment comment) {
        if (comments == null) {
            comments = List.of(comment);
        } else {
            comments.add(comment);
        }
    }
}
