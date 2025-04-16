package web.mapper;

import domain.model.User;
import org.springframework.stereotype.Component;
import web.model.UserDto;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        return new UserDto(user.getUuid());
    }
}
