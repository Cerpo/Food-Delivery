package com.cerpo.fd;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;
import com.cerpo.fd.model.user.Role;
import com.cerpo.fd.model.user.User;
import com.cerpo.fd.model.user.UserRepository;
import com.cerpo.fd.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class Config {
    private final UserRepository   userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncored());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncored() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/java/com/cerpo/fd/JWTTokens.txt"));
            var customer = new User ("customer@test.com", passwordEncored().encode("1234"),
                                      AppUtils.getDate(null), Role.ROLE_CUSTOMER);
            var retailer = new User ("retailer@test.com", passwordEncored().encode("1234"),
                                      AppUtils.getDate(null), Role.ROLE_RETAILER);
            var courier  = new User ("courier@test.com", passwordEncored().encode("1234"),
                                      AppUtils.getDate(null), Role.ROLE_COURIER);
            var admin    = new User ("admin@test.com", passwordEncored().encode("1234"),
                                      AppUtils.getDate(null), Role.ROLE_ADMIN);
            writer.append("Customer's JWT Token: " + jwtTokenProvider.generateToken(customer) + "\n");
            writer.append("Retailer's JWT Token: " + jwtTokenProvider.generateToken(retailer) + "\n");
            writer.append("Courier's JWT Token: " + jwtTokenProvider.generateToken(courier) + "\n");
            writer.append("Admin's JWT Token: " + jwtTokenProvider.generateToken(admin) + "\n");
            writer.close();
            userRepository.saveAll(List.of(customer, retailer, courier, admin));
        };
    }
}
