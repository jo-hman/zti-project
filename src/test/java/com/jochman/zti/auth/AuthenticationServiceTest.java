package com.jochman.zti.auth;

import com.jochman.zti.auth.model.request.UserRequest;
import com.jochman.zti.auth.model.response.AuthTokenResponse;
import com.jochman.zti.auth.repository.User;
import com.jochman.zti.auth.repository.UserRepository;
import com.jochman.zti.auth.service.AuthenticationService;
import com.jochman.zti.auth.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testCreateUser_UserAlreadyExists() {
        // given
        UserRequest userRequest = new UserRequest("test@example.com", "password123");
        User existingUser = new User(UUID.randomUUID().toString(), "test@example.com", "password123");

        when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.of(existingUser));

        // when
        Optional<AuthTokenResponse> authTokenResponse = authenticationService.createUser(userRequest);

        // then
        assertFalse(authTokenResponse.isPresent());
        verify(userRepository, never()).save(any(User.class));
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    public void testCreateUser_NewUser() {
        // given
        UserRequest userRequest = new UserRequest("new@example.com", "password123");
        User newUser = new User(UUID.randomUUID().toString(), userRequest.email(), userRequest.password());

        when(userRepository.findByEmail(userRequest.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(newUser);
        when(jwtService.generateToken(any(User.class))).thenReturn("mocked-token");

        // when
        Optional<AuthTokenResponse> authTokenResponse = authenticationService.createUser(userRequest);

        // then
        assertTrue(authTokenResponse.isPresent());
        assertEquals("mocked-token", authTokenResponse.get().authToken());
        verify(userRepository).save(any(User.class));
        verify(jwtService).generateToken(any(User.class));
    }

    @Test
    public void testGetToken_ValidCredentials() {
        // given
        UserRequest userRequest = new UserRequest("valid@example.com", "password123");
        User user = new User(UUID.randomUUID().toString(), userRequest.email(), userRequest.password());

        when(userRepository.findByEmailAndPassword(userRequest.email(), userRequest.password())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("valid-token");

        // when
        Optional<AuthTokenResponse> authTokenResponse = authenticationService.getToken(userRequest);

        // then
        assertTrue(authTokenResponse.isPresent());
        assertEquals("valid-token", authTokenResponse.get().authToken());
        verify(userRepository).findByEmailAndPassword(userRequest.email(), userRequest.password());
        verify(jwtService).generateToken(user);
    }

    @Test
    public void testGetToken_InvalidCredentials() {
        // given
        UserRequest userRequest = new UserRequest("invalid@example.com", "wrongpassword");

        when(userRepository.findByEmailAndPassword(userRequest.email(), userRequest.password())).thenReturn(Optional.empty());

        // when
        Optional<AuthTokenResponse> authTokenResponse = authenticationService.getToken(userRequest);

        // then
        assertFalse(authTokenResponse.isPresent());
        verify(userRepository).findByEmailAndPassword(userRequest.email(), userRequest.password());
        verify(jwtService, never()).generateToken(any(User.class));
    }

    @Test
    public void testGetUsers() {
        // given
        List<User> users = List.of(
                new User(UUID.randomUUID().toString(), "user1@example.com", "password1"),
                new User(UUID.randomUUID().toString(), "user2@example.com", "password2")
        );

        given(userRepository.findAll()).willReturn(users);

        // when
        List<User> result = authenticationService.getUsers();

        // then
        assertEquals(2, result.size());
        assertEquals("user1@example.com", result.get(0).getEmail());
        assertEquals("user2@example.com", result.get(1).getEmail());
        verify(userRepository).findAll();
    }
}
