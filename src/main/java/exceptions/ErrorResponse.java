package exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final String message;
    private final String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp;

    public ErrorResponse(RuntimeException ex, HttpStatus status) {
        this.message = ex.getMessage();
        this.status = status.toString();
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
