package web.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class WaitingGameDto {

    private UUID uuid;
    @JsonProperty("X Player")
    private String xPlayer;

    @JsonProperty("O Player")
    private String oPlayer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @JsonProperty("X Player")
    public String getXPlayer() {
        return xPlayer;
    }

    public void setXPlayer(String xPlayer) {
        this.xPlayer = xPlayer;
    }

    @JsonProperty("O Player")
    public String getOPlayer() {
        return oPlayer;
    }

    public void setOPlayer(String oPlayer) {
        this.oPlayer = oPlayer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
