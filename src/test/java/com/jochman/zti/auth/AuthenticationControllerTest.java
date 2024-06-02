package com.jochman.zti.auth;

import com.jochman.zti.auth.controller.AuthenticationController;
import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.model.response.UserResponse;
import com.jochman.zti.auth.repository.User;
import com.jochman.zti.auth.service.AuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthenticationController authenticationController;

    private User user;
    private AuthTokenResponse authTokenResponse;

    @BeforeEach
    public void setUp() {
        user = new User(UUID.randomUUID().toString(), "test@example.com", "password123");
        authTokenResponse = new AuthTokenResponse("testToken");
    }

    @Test
    public void testCreateUser_Success() {
        UserRequest userRequest = new UserRequest("test@example.com", "password123");

        when(authenticationService.createUser(any(UserRequest.class))).thenReturn(Optional.of(authTokenResponse));

        ResponseEntity<AuthTokenResponse> response = authenticationController.createUser(userRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("testToken", response.getBody().authToken());
        verify(authenticationService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    public void testCreateUser_Failure() {
        UserRequest userRequest = new UserRequest("test@example.com", "password123");

        when(authenticationService.createUser(any(UserRequest.class))).thenReturn(Optional.empty());

        ResponseEntity<AuthTokenResponse> response = authenticationController.createUser(userRequest);

        assertEquals(400, response.getStatusCodeValue());
        verify(authenticationService, times(1)).createUser(any(UserRequest.class));
    }

    @Test
    public void testGetToken_Success() {
        UserRequest userRequest = new UserRequest("test@example.com", "password123");

        when(authenticationService.getToken(any(UserRequest.class))).thenReturn(Optional.of(authTokenResponse));

        ResponseEntity<AuthTokenResponse> response = authenticationController.getToken(userRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("testToken", response.getBody().authToken());
        verify(authenticationService, times(1)).getToken(any(UserRequest.class));
    }

    @Test
    public void testGetToken_Failure() {
        UserRequest userRequest = new UserRequest("test@example.com", "password123");

        when(authenticationService.getToken(any(UserRequest.class))).thenReturn(Optional.empty());

        ResponseEntity<AuthTokenResponse> response = authenticationController.getToken(userRequest);

        assertEquals(400, response.getStatusCodeValue());
        verify(authenticationService, times(1)).getToken(any(UserRequest.class));
    }

    @Test
    public void testGetUsers_Success() {
        List<User> users = List.of(user);
        UserResponse userResponse = new UserResponse(user.getId(), user.getEmail());

        when(authenticationService.getUsers()).thenReturn(users);

        ResponseEntity<List<UserResponse>> response = authenticationController.getUsers();

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(userResponse.id(), response.getBody().get(0).id());
        assertEquals(userResponse.email(), response.getBody().get(0).email());
        verify(authenticationService, times(1)).getUsers();
    }
}
