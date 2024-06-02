package com.jochman.zti.post.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Repository interface for managing Post entities.
 */
public interface PostsRepository extends MongoRepository<Post, String> {
}
