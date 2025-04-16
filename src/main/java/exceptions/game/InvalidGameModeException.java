package exceptions.game;

public class InvalidGameModeException extends RuntimeException {

    public InvalidGameModeException(String message) {
        super(message);
    }
}
