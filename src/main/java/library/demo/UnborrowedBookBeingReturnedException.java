package library.demo;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnborrowedBookBeingReturnedException extends RuntimeException {
    public UnborrowedBookBeingReturnedException(){
        super ("Book is not borrowed, hence cannot be returned");
    }
}
