package library.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/library")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @PostMapping("/add")
    public void addBook(@RequestBody Book book){
        bookRepository.save(book);
    }

    @GetMapping("/fetchAll")
    public List<Book> fetchAll (){
        return bookRepository.findAll();
    }
}
