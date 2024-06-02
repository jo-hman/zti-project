package com.jochman.zti.auth.model.request;

/**
 * Represents a user request object containing email and password.
 */
public record UserRequest(String email, String password) {
}
