package com.cerpo.fd.service;

import com.cerpo.fd.exception.FDApiException;
import com.cerpo.fd.model.user.Role;
import com.cerpo.fd.model.user.User;
import com.cerpo.fd.model.user.UserRepository;
import com.cerpo.fd.payload.auth.AuthenticationResponse;
import com.cerpo.fd.payload.auth.SignInRequest;
import com.cerpo.fd.payload.auth.SignUpRequest;
import com.cerpo.fd.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Date;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TestAuthService {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        authenticationService = new AuthenticationService(userRepository, passwordEncoder, authenticationManager, jwtTokenProvider);
    }

    @Test
    public void testAuthenticate() {
        var user = new User("Test@test.com", new BCryptPasswordEncoder().encode("TestPW"), new Date(), Role.ROLE_CUSTOMER);
        var jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lckB0ZXN0LmNvbSIsImlhdCI6MTY5NDI3ODg3MCwiZXhwIjoxNjk0MjgyNDcwfQ.iKv0ZZDJZgTjXQjkrB5mpYmlHuuMOW12qtoQ_uEOYsw";

        given(userRepository.findByEmail("Test@test.com")).willReturn(Optional.of(user));
        given(jwtTokenProvider.generateToken(any())).willReturn(jwt);

        SignInRequest request = new SignInRequest();
        request.setEmail("Test@test.com");
        request.setPassword("TestPW");

        AuthenticationResponse resp = authenticationService.authenticate(request);
        assertThat(resp.jwtToken()).isEqualTo(jwt);
        verify(userRepository).save(user);
    }

    @Test
    public void testAuthenticateBadCredentials() {
        given(userRepository.findByEmail("Test@test.com")).willReturn(Optional.empty());

        SignInRequest request = new SignInRequest();
        request.setEmail("Test@test.com");
        request.setPassword("TestPW");

        assertThatThrownBy(() -> authenticationService.authenticate(request))
                .isInstanceOf(FDApiException.class)
                .hasMessageContaining("Bad credentials");
        verify(authenticationManager, never()).authenticate(any());
    }

    @Test
    public void testRegister() {
        var jwt = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJjdXN0b21lckB0ZXN0LmNvbSIsImlhdCI6MTY5NDI3ODg3MCwiZXhwIjoxNjk0MjgyNDcwfQ.iKv0ZZDJZgTjXQjkrB5mpYmlHuuMOW12qtoQ_uEOYsw";

        given(userRepository.findByEmail("Test@test.com")).willReturn(Optional.empty());
        given(jwtTokenProvider.generateToken(any())).willReturn(jwt);

        SignUpRequest request = new SignUpRequest();
        request.setEmail("Test@test.com");
        request.setPassword("TestPW");

        AuthenticationResponse resp = authenticationService.register(request);
        assertThat(resp.jwtToken()).isEqualTo(jwt);
        verify(userRepository).save(any());
    }

    @Test
    public void testRegisterEmailTaken() {
        var user = new User("Test@test.com", new BCryptPasswordEncoder().encode("TestPW"), new Date(), Role.ROLE_CUSTOMER);

        given(userRepository.findByEmail("Test@test.com")).willReturn(Optional.of(user));

        SignUpRequest request = new SignUpRequest();
        request.setEmail("Test@test.com");
        request.setPassword("TestPW");

        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(FDApiException.class)
                .hasMessageContaining("Email is already taken");
        verify(userRepository, never()).save(any());
    }
}
