package com.michael.libertybank.security;

import com.michael.libertybank.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

import static com.michael.libertybank.util.GeneralConstants.*;
import static java.util.stream.Collectors.toList;


@Slf4j
@Service
public class JwtService {
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.get("userId").toString());
    }

    public List getUserAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("authorities", List.class);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateJwtToken(User user) {
        return generateToken(new HashMap<>(), user);
    }



    public String generateToken(Map<String, Object> extraClaims, User user) {
        List<String> authorities = user.getAuthorities().stream().map(
                GrantedAuthority::getAuthority).collect(toList());
        Long userId = user.getId();
        extraClaims.put("authorities", authorities);
        extraClaims.put("userId", userId);
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            log.error("Invalid JWT token. Possibly expired");
            throw ex;
        }
    }


    public Optional<String> retrieveJWT(HttpServletRequest request) {
        if (hasBearerToken(request)) {
            return Optional.of(retrieveToken(request).substring(7));
        }
        return Optional.empty();
    }

    public boolean hasBearerToken(HttpServletRequest request) {
        String token = retrieveToken(request);
        return StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX);
    }

    private String retrieveToken(HttpServletRequest request) {
        return request.getHeader(HEADER_STRING);
    }
}