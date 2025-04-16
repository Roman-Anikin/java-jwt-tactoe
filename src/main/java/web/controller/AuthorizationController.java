package web.controller;

import domain.service.SignUpRequestService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import security.model.JwtRequest;
import security.model.JwtResponse;
import security.model.RefreshJwtRequest;
import web.model.SignUpRequest;

@RestController
@RequestMapping("/auth")
public class AuthorizationController {

    private final SignUpRequestService service;

    public AuthorizationController(SignUpRequestService service) {
        this.service = service;
    }

    @PostMapping("/reg")
    public String userRegistration(@RequestBody SignUpRequest request) {
        return service.userRegistration(request);
    }

    @PostMapping("/login")
    public JwtResponse userLogin(@RequestBody JwtRequest request) {
        return service.userLogin(request);
    }

    @PostMapping("/access")
    public JwtResponse updateAccessToken(@RequestBody RefreshJwtRequest request) {
        return service.updateAccessToken(request.getRefreshToken());
    }

    @PostMapping("/refresh")
    public JwtResponse updateRefreshToken(@RequestBody RefreshJwtRequest request) {
        return service.updateRefreshToken(request.getRefreshToken());
    }
}
