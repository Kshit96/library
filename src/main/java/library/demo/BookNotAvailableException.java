package library.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException() {
        super("Book is not available");
    }
}
