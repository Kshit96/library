package library.demo.lending;

import library.demo.BookNotAvailableException;
import library.demo.UnborrowedBookBeingReturnedException;
import library.demo.author.AuthorRepository;
import library.demo.book.BookRepository;
import library.demo.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/library")
public class LendingController {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    LendingRepository lendingRepository;

    @Autowired
    LendingServiceLibrary lendingServiceLibrary;

    @PostMapping("/borrow")
    public void borrowBook(@RequestBody LendingRecord lendingRecord) {

        if (bookRepository.findById(lendingRecord.getBook().getId()).get().getAvailableCopies() == 0) {
            throw new BookNotAvailableException();
        }

        lendingServiceLibrary.borrowBook(lendingRecord);
    }

    @PostMapping("/return")
    public void returnBook(@RequestBody LendingRecord lendingRecord) throws UnborrowedBookBeingReturnedException {
        if(!lendingRepository.existsById(lendingRecord.getId()) || lendingRepository.findById(lendingRecord.getId()).get().getState() == State.RETURNED) {
            throw new UnborrowedBookBeingReturnedException();
        }
        lendingServiceLibrary.returnBook(lendingRecord);

    }
}
