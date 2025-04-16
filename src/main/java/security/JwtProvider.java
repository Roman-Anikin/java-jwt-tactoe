package security;

import domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.access.secret}")
    private String accessSecret;

    @Value("${jwt.refresh.secret}")
    private String refreshSecret;

    @Value("${jwt.access.expiration-minutes}")
    private int accessExpMinutes;

    @Value("${jwt.refresh.expiration-days}")
    private int refreshExpDays;

    private SecretKey accessKey;
    private SecretKey refreshKey;

    @PostConstruct
    public void initKeys() {
        accessKey = getSigningKey(accessSecret);
        refreshKey = getSigningKey(refreshSecret);
    }

    public String generateAccessToken(User user) {
        Instant instant = LocalDateTime.now().plusMinutes(accessExpMinutes).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .subject(user.getLogin())
                .signWith(accessKey)
                .expiration(Date.from(instant))
                .claim("uuid", user.getUuid())
                .claim("roles", user.getRoles())
                .compact();
    }

    public String generateRefreshToken(User user) {
        Instant instant = LocalDateTime.now().plusDays(refreshExpDays).atZone(ZoneId.systemDefault()).toInstant();
        return Jwts.builder()
                .subject(user.getLogin())
                .signWith(refreshKey)
                .expiration(Date.from(instant))
                .claim("uuid", user.getUuid())
                .compact();
    }

    public void validateAccessToken(String token) {
        getClaims(accessKey, token);
    }

    public void validateRefreshToken(String token) {
        getClaims(refreshKey, token);
    }

    public Claims getAccessClaims(String token) {
        return getClaims(accessKey, token);
    }

    public Claims getRefreshClaims(String token) {
        return getClaims(refreshKey, token);
    }

    private SecretKey getSigningKey(String secret) {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims getClaims(SecretKey key, String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
