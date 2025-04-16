package web.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public class CurrentGameDto {

    private final GameFieldDto gameField;
    private UUID activePlayer;
    private boolean isDraw;
    private UUID winner;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime created;

    public CurrentGameDto() {
        gameField = new GameFieldDto();
    }

    public CurrentGameDto(GameFieldDto field) {
        this.gameField = field;
    }

    public GameFieldDto getGameField() {
        return gameField;
    }

    public UUID getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(UUID activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}
