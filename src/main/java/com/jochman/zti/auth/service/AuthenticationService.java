package com.jochman.zti.auth.service;

import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.model.response.UserResponse;
import com.jochman.zti.auth.repository.User;
import com.jochman.zti.auth.repository.UserRepository;
import io.micrometer.observation.ObservationFilter;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for handling authentication-related operations.
 */
@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    /**
     * Creates a new user and generates an authentication token.
     * @param userRequest the user request object containing user details
     * @return an optional containing an authentication token response if successful, otherwise empty
     */
    public Optional<AuthTokenResponse> createUser(UserRequest userRequest) {
        return userRepository.findByEmail(userRequest.email())
                .map(user -> Optional.<AuthTokenResponse>empty())
                .orElseGet(() -> getAuthTokenAndCreateUser(userRequest));
    }

    private Optional<AuthTokenResponse> getAuthTokenAndCreateUser(UserRequest userRequest) {
        var user = userRepository.save(new User(UUID.randomUUID().toString(), userRequest.email(), userRequest.password()));
        return getAuthToken(user);
    }

    private Optional<AuthTokenResponse> getAuthToken(User user) {
        return Optional.of(new AuthTokenResponse(jwtService.generateToken(user)));
    }

    /**
     * Retrieves an authentication token for an existing user.
     * @param userRequest the user request object containing user credentials
     * @return an optional containing an authentication token response if successful, otherwise empty
     */
    public Optional<AuthTokenResponse> getToken(UserRequest userRequest) {
        var user = userRepository.findByEmailAndPassword(userRequest.email(), userRequest.password());
        return user
                .flatMap(this::getAuthToken);
    }

    /**
     * Retrieves a list of all users.
     * @return a list of users
     */
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
