package com.cerpo.fd.service;

import com.cerpo.fd.model.user.Role;
import com.cerpo.fd.model.user.User;
import com.cerpo.fd.model.user.UserRepository;
import com.cerpo.fd.payload.AuthenticationRequest;
import com.cerpo.fd.payload.AuthenticationResponse;
import com.cerpo.fd.payload.RegisterRequest;
import com.cerpo.fd.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository        userRepository;
    private final PasswordEncoder       passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider      jwtTokenProvider;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_CUSTOMER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder().jwtToken(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtTokenProvider.generateToken(user);
        return AuthenticationResponse.builder().jwtToken(jwtToken).build();
    }
}
