package com.jochman.zti.post.repository;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Comment {

    @Field
    private String comment;
}
