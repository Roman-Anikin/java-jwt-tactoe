package domain.service;

import datasource.repository.UserRepository;
import domain.model.Role;
import domain.model.User;
import exceptions.user.IncorrectPasswordException;
import exceptions.user.UserAlreadyExistException;
import exceptions.user.UserNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import security.JwtAuthContextService;
import web.model.SignUpRequest;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder encoder;
    private final JwtAuthContextService jwtAuthContextService;

    public UserServiceImpl(UserRepository repository,
                           BCryptPasswordEncoder encoder,
                           JwtAuthContextService jwtAuthContextService) {
        this.repository = repository;
        this.encoder = encoder;
        this.jwtAuthContextService = jwtAuthContextService;
    }

    @Override
    public String register(SignUpRequest request) {
        repository.findByLogin(request.getLogin())
                .ifPresent(user -> {
                    throw new UserAlreadyExistException("User with login: " + request.getLogin() + " already exist");
                });
        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(encoder.encode(request.getPassword()));
        user.getRoles().add(Role.USER);
        repository.save(user);
        return "Success";
    }

    @Override
    public User login(String login, String password) {
        return validateUser(login, password);
    }

    @Override
    public User getByUuid(UUID uuid) {
        return repository.findByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User with uuid: " + uuid + " not found"));
    }

    @Override
    public User getByAccessToken() {
        return getByUuid(jwtAuthContextService.getUserUuidByAccessToken());
    }

    private User validateUser(String login, String password) {
        Optional<User> user = repository.findByLogin(login);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with login: " + login + " not found");
        }
        if (!encoder.matches(password, user.get().getPassword())) {
            throw new IncorrectPasswordException("Incorrect password");
        }
        return user.get();
    }
}
