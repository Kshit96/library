package library.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/library")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping("/add")
    public void addBook(@RequestBody Book book){
        bookRepository.save(book);
    }

    @GetMapping("/fetchAll")
    public List<Book> fetchAll (){
        return bookRepository.findAll();
    }
}
