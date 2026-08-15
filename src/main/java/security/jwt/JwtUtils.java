package security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtils {


    private String jwtSecret;
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication){
        authentication.getPrincipal();

        //return Jwts.builder().setSubject(user);

        return null;

    }
}
