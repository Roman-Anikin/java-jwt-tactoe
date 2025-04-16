package exceptions;

import exceptions.game.*;
import exceptions.security.RefreshTokenNotFoundException;
import exceptions.user.IncorrectPasswordException;
import exceptions.user.UserAlreadyExistException;
import exceptions.user.UserNotFoundException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidGameException(InvalidFieldException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistException(UserAlreadyExistException exception) {
        return new ErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIncorrectPasswordException(IncorrectPasswordException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleGameNotFoundException(GameNotFoundException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidBadgeException(InvalidBadgeException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidGameModeException(InvalidGameModeException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleBadgeAlreadyTakenException(BadgeAlreadyTakenException exception) {
        return new ErrorResponse(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleRefreshTokenNotFoundException(RefreshTokenNotFoundException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleJwtException(JwtException exception) {
        return new ErrorResponse(exception, HttpStatus.BAD_REQUEST);
    }
}