package security;

import domain.model.Role;
import io.jsonwebtoken.Claims;
import security.model.JwtAuthentication;

import java.util.List;
import java.util.UUID;

public class JwtUtil {

    public static JwtAuthentication createByClaims(Claims claims) {
        List<String> roleClaim = claims.get("roles", List.class);
        List<Role> roles = roleClaim.stream()
                .map(Role::valueOf)
                .toList();
        return new JwtAuthentication(UUID.fromString(claims.get("uuid", String.class)), roles);
    }
}
