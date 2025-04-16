package web.controller;

import domain.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.mapper.UserMapper;
import web.model.UserDto;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserMapper mapper;
    private final UserService service;

    public UserController(UserMapper mapper, UserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/{uuid}")
    public UserDto getByUuid(@PathVariable("uuid") UUID uuid) {
        return mapper.toDto(service.getByUuid(uuid));
    }

    @GetMapping
    public UserDto getByAccessToken() {
        return mapper.toDto(service.getByAccessToken());
    }
}
