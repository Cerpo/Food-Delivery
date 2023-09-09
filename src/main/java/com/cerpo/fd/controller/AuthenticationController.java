package com.cerpo.fd.controller;

import com.cerpo.fd.payload.auth.SignInRequest;
import com.cerpo.fd.payload.auth.AuthenticationResponse;
import com.cerpo.fd.payload.auth.SignUpRequest;
import com.cerpo.fd.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody SignInRequest request) {
        return new ResponseEntity<>(authenticationService.authenticate(request), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody SignUpRequest request) {
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.OK);
    }
}
