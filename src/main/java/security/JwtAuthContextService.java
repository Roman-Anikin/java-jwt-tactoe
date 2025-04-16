package security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import security.model.JwtAuthentication;

import java.util.UUID;

@Service
public class JwtAuthContextService {

    public JwtAuthentication getJwtAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

    public UUID getUserUuidByAccessToken() {
        return (UUID) getJwtAuthentication().getPrincipal();
    }
}
