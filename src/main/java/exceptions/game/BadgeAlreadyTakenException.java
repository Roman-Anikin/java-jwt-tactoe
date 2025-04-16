package exceptions.game;

public class BadgeAlreadyTakenException extends RuntimeException {

    public BadgeAlreadyTakenException(String message) {
        super(message);
    }
}
