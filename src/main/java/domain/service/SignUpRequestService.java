package domain.service;

import security.model.JwtAuthentication;
import security.model.JwtRequest;
import security.model.JwtResponse;
import web.model.SignUpRequest;

public interface SignUpRequestService {

    String userRegistration(SignUpRequest request);

    JwtResponse userLogin(JwtRequest request);

    JwtResponse updateAccessToken(String token);

    JwtResponse updateRefreshToken(String token);

    JwtAuthentication getJwtAuthentication();
}
