package domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "current_game")
@NamedEntityGraph(
        name = "game.field",
        attributeNodes = @NamedAttributeNode("gameField")
)
public class CurrentGame {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "uuid", nullable = false)
    private UUID uuid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "game_field_id")
    private GameField gameField;

    @Column(name = "waiting")
    private boolean isWaiting;

    @Column(name = "active_player")
    private UUID activePlayer;

    @Column(name = "draw")
    private boolean isDraw;
    private UUID winner;

    @Column(name = "x_player")
    private UUID xPlayer;

    @Column(name = "o_player")
    private UUID oPlayer;

    private LocalDateTime created;

    public CurrentGame() {
        gameField = new GameField();
        created = LocalDateTime.now();
    }

    public CurrentGame(GameField field, UUID uuid) {
        this.gameField = field;
        this.uuid = uuid;
    }

    public GameField getGameField() {
        return gameField;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean isWaiting() {
        return isWaiting;
    }

    public void setWaiting(boolean waiting) {
        this.isWaiting = waiting;
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
        this.isDraw = draw;
    }

    public UUID getWinner() {
        return winner;
    }

    public void setWinner(UUID winner) {
        this.winner = winner;
    }

    public UUID getXPlayer() {
        return xPlayer;
    }

    public void setXPlayer(UUID xPlayer) {
        this.xPlayer = xPlayer;
    }

    public UUID getOPlayer() {
        return oPlayer;
    }

    public void setOPlayer(UUID oPlayer) {
        this.oPlayer = oPlayer;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "CurrentGame{" +
                "uuid=" + uuid +
                ", gameField=" + gameField +
                ", isWaiting=" + isWaiting +
                ", activePlayer=" + activePlayer +
                ", isDraw=" + isDraw +
                ", winner=" + winner +
                ", xPlayer=" + xPlayer +
                ", oPlayer=" + oPlayer +
                ", created=" + created +
                '}';
    }
}
