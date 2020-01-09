package logic.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.SignatureAlgorithm;
import logic.services.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.function.Function;

/**
 * Класс, обсулживающий JsonWebToken-ы.
 * @author Артемий Кульбако
 * @version 1.1
 */
@Component
public class JWTUtil {

    private final String KEY = "liquid";
    private static final long TOKEN_VALIDITY = 604800000; //1 неделя
    private final UserDetailsServiceImpl userDetails;
    private final Logger logger;

    @Autowired public JWTUtil(UserDetailsServiceImpl userDetails, Logger logger) {
        this.userDetails = userDetails;
        this.logger = logger;
    }

    public String generateToken(String username, List<String> roles) {
        logger.info("Генерируем токен для " + username);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", roles);
        Date now = new Date();
        return Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(new Date(now.getTime() + TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, KEY).compact();
    }

    public boolean validateToken(String token) {
        Jws<Claims> claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
        if (claims.getBody().getExpiration().before(new Date())) {
            logger.info("Токен невалиден");
            return false;
        } else return true;
    }

    private boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String resolveToken(HttpServletRequest req) {
        logger.info("Проверяем запрос " + req + " на наличие токена");
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails ud = this.userDetails.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(ud, "", ud.getAuthorities());
    }

    public String getUsername(String token) {
        return getClaim(token, Claims::getSubject);
    }

    public Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }
}