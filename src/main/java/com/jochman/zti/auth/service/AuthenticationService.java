package com.jochman.zti.auth.service;

import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.repository.User;
import com.jochman.zti.auth.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

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
}
