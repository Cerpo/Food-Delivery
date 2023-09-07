package com.cerpo.fd.payload.auth;

public record AuthenticationResponse(String tokenType, String jwtToken) {
}
