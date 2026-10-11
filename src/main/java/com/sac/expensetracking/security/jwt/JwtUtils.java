package com.sac.expensetracking.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.sac.expensetracking.security.services.UserDetailsImpl;

import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${expensetracking.app.jwtSecret}")
    private String jwtSecret;

    @Value("${expensetracking.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    @PostConstruct
    public void logJwtConfiguration() {
        try {
            byte[] decoded = Decoders.BASE64.decode(jwtSecret);

            logger.info(
                    "JWT configuration: secretPresent={}, decodedKeyBytes={}, expirationMs={}",
                    jwtSecret != null && !jwtSecret.isBlank(),
                    decoded.length,
                    jwtExpirationMs
            );

            // Validate HS256 key strength without logging the key.
            Keys.hmacShaKeyFor(decoded);
            logger.info("JWT signing key has acceptable strength");
        } catch (Exception e) {
            logger.error("JWT configuration or key validation failed: {}",
                    e.getClass().getSimpleName());
        }
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(authToken);
            logger.info("JWT validation succeeded");
            return true;
        } catch (io.jsonwebtoken.security.SignatureException e) {
            logger.error("JWT signature validation failed", e);
        } catch (ExpiredJwtException e) {
            logger.error("JWT has expired", e);
        } catch (MalformedJwtException e) {
            logger.error("JWT is malformed", e);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT is unsupported", e);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid JWT argument", e);
        }

        return false;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
}
