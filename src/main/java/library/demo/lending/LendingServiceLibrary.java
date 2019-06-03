package library.demo.lending;

import library.demo.book.Book;
import library.demo.book.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LendingServiceLibrary implements LendingService {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    LendingRepository lendingRepository;

    @Override
    public void borrowBook(LendingRecord lendingRecord) {
        Book currentBook = bookRepository.findById(lendingRecord.getBook().getId()).get();
        currentBook.setAvailableCopies(currentBook.getAvailableCopies()-1);
        bookRepository.save(currentBook);
    }

    @Override
    public void returnBook(LendingRecord lendingRecord) {
            Book currentBook = bookRepository.findById(lendingRecord.getBook().getId()).get();
            currentBook.setAvailableCopies(currentBook.getAvailableCopies() + 1);
            bookRepository.save(currentBook);
    }
}
