package domain.service;

import domain.model.User;
import web.model.SignUpRequest;

import java.util.UUID;

public interface UserService {

    String register(SignUpRequest request);

    User login(String login, String password);

    User getByUuid(UUID uuid);

    User getByAccessToken();

}
