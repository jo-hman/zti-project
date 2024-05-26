package com.jochman.zti.post.repository;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
}
