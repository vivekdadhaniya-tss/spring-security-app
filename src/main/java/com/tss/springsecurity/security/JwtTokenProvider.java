package com.tss.springsecurity.security;

import com.tss.springsecurity.exception.UserApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoder;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    @Value("${app-jwt-secret}")
    private String jwtSecretKey;

    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    public String generateToken(Authentication authentication) {

        String username = authentication.getName();
        Date currentDate = new Date();
        Date expiryDate = new Date(currentDate.getTime() + jwtExpirationDate);

        // Convert authorities to a list of Strings (e.g., ["ROLE_USER"])
        // This prevents serialization issues
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiryDate)
                .claim("roles", roles) // Use the String list here
                .signWith(key())
                .compact();

    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(token);
            return true;
        } catch (MalformedJwtException e) {
            throw new UserApiException("Invalid Jwt Token");
        } catch (ExpiredJwtException e) {
            throw new UserApiException("Expired Jwt Token");
        } catch (UnsupportedJwtException e) {
            throw new UserApiException("Unsupported Jwt Token");
        } catch (IllegalArgumentException e) {
            throw new UserApiException("Jwt claims string is empty");
        } catch (Exception e) {
            throw new UserApiException("Invalid Credentials");
        }
    }

    public String getUsername(String token){
        Claims claims = Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        String username = claims.getSubject();
        return username;
    }
}
