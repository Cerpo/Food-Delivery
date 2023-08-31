package com.cerpo.fd.security;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider   jwtTokenProvider;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authToken;
        UserDetails                         userDetails;
        String                              authHeader;
        String                              jwtToken;
        String                              userName;

        authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
        } else {
            jwtToken = authHeader.substring(7);
            userName = jwtTokenProvider.extractUsername(jwtToken);
            if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) { //fixme Ez nekem gyanús
                userDetails = userDetailsService.loadUserByUsername(userName);
                if (jwtTokenProvider.isTokenValid(jwtToken, userDetails)) {
                    authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authToken);
                }
            }
            filterChain.doFilter(request, response);
        }
    }
}
