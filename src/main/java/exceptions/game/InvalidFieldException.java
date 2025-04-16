package exceptions.game;

public class InvalidFieldException extends RuntimeException {

    public InvalidFieldException(String message) {
        super(message);
    }
}
