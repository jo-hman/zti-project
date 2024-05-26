package com.jochman.zti.auth.controller;

import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("users")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<AuthTokenResponse> createUser(@RequestBody UserRequest userRequest) {
        return authenticationService.createUser(userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PostMapping("/authorize")
    public ResponseEntity<AuthTokenResponse> getToken(@RequestBody UserRequest userRequest) {
        return authenticationService.getToken(userRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
