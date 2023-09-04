package com.cerpo.fd.payload.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private final String tokenType = "Bearer";

    private String jwtToken;
}
