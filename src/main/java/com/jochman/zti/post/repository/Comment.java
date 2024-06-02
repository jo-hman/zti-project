package com.jochman.zti.post.repository;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * Represents a comment entity in the database.
 */
@Data
public class Comment {

    @Field
    private String comment;
}
