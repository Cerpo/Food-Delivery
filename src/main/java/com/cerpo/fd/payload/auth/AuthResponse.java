package com.cerpo.fd.payload.auth;

public record AuthResponse(String tokenType, String jwtToken) {
}
