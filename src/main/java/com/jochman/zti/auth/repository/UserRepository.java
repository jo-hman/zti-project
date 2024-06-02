package com.jochman.zti.auth.repository;

import com.jochman.zti.auth.model.response.AuthTokenResponse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing User entities.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    /**
     * Finds a user by email.
     * @param email the email of the user to find
     * @return an optional containing the user if found, otherwise empty
     */
    Optional<User> findByEmail(String email);

    /**
     * Finds a user by email and password.
     * @param email the email of the user to find
     * @param password the password of the user to find
     * @return an optional containing the user if found, otherwise empty
     */
    Optional<User> findByEmailAndPassword(String email, String password);
}
