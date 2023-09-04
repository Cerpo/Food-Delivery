package com.cerpo.fd.security;

import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Function;
import java.security.Key;
import com.cerpo.fd.AppUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;

@Component
public class JwtTokenProvider {
    private static final String SECRET_KEY = "75da249f774b70f678851298682c37bfd331d9b84c67e8c83e3ee3f6d423cdc0";

    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaim(jwtToken, Claims::getExpiration);
    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResorver) {
        return claimsResorver.apply(extractAllClaims(jwtToken));
    }

    private Claims extractAllClaims(String jwtToken) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }

    public boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        return (extractUsername(jwtToken).equals(userDetails.getUsername())) && !isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(AppUtils.getDate(null))
                .setExpiration(AppUtils.getDate(AppUtils.getJWTExpiry()))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
