package web.model;

import java.util.UUID;

public class UserDto {

    private UUID uuid;

    public UserDto() {
    }

    public UserDto(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
