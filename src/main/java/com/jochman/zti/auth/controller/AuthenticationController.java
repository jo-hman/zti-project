package com.jochman.zti.auth.controller;

import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.model.response.UserResponse;
import com.jochman.zti.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling authentication-related requests.
 */
@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    /**
     * Endpoint for creating a new user.
     * @param userRequest the user request object containing user details
     * @return ResponseEntity containing an authentication token response if successful, else a bad request response
     */
    @PostMapping
    public ResponseEntity<AuthTokenResponse> createUser(@RequestBody UserRequest userRequest) {
        return authenticationService.createUser(userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint for obtaining an authentication token.
     * @param userRequest the user request object containing user credentials
     * @return ResponseEntity containing an authentication token response if successful, else a bad request response
     */
    @PostMapping("/authorize")
    public ResponseEntity<AuthTokenResponse> getToken(@RequestBody UserRequest userRequest) {
        return authenticationService.getToken(userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    /**
     * Endpoint for retrieving a list of users.
     * @return ResponseEntity containing a list of user responses
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getUsers() {
        return ResponseEntity.ok(authenticationService.getUsers().stream()
                .map(user -> new UserResponse(user.getId(), user.getEmail())).toList());
    }
}
