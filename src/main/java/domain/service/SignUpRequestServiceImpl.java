package domain.service;

import domain.model.User;
import exceptions.security.RefreshTokenNotFoundException;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;
import security.JwtAuthContextService;
import security.JwtProvider;
import security.model.JwtAuthentication;
import security.model.JwtRequest;
import security.model.JwtResponse;
import web.model.SignUpRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SignUpRequestServiceImpl implements SignUpRequestService {

    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final JwtAuthContextService jwtAuthContextService;
    private final Map<String, String> refreshTokens;

    public SignUpRequestServiceImpl(UserService userService,
                                    JwtProvider jwtProvider,
                                    JwtAuthContextService jwtAuthContextService) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
        this.jwtAuthContextService = jwtAuthContextService;
        refreshTokens = new HashMap<>();
    }

    @Override
    public String userRegistration(SignUpRequest request) {
        return userService.register(request);
    }

    @Override
    public JwtResponse userLogin(JwtRequest request) {
        User user = userService.login(request.getLogin(), request.getPassword());
        String accessToken = jwtProvider.generateAccessToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokens.put(user.getLogin(), refreshToken);
        return new JwtResponse(accessToken, refreshToken);
    }

    @Override
    public JwtResponse updateAccessToken(String token) {
        User user = getUserFromToken(token);
        return new JwtResponse(jwtProvider.generateAccessToken(user), null);
    }

    @Override
    public JwtResponse updateRefreshToken(String token) {
        User user = getUserFromToken(token);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        refreshTokens.put(user.getLogin(), refreshToken);
        return new JwtResponse(null, refreshToken);
    }

    @Override
    public JwtAuthentication getJwtAuthentication() {
        return jwtAuthContextService.getJwtAuthentication();
    }

    private User getUserFromToken(String token) {
        jwtProvider.validateRefreshToken(token);
        Claims claims = jwtProvider.getRefreshClaims(token);
        User user = userService.getByUuid(UUID.fromString(claims.get("uuid", String.class)));
        if (!token.equals(refreshTokens.get(user.getLogin()))) {
            throw new RefreshTokenNotFoundException("Invalid refresh token");
        }
        return user;
    }
}
