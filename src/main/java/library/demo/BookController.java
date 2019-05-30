package library.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/library",consumes = MediaType.APPLICATION_JSON_VALUE)
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @PostMapping(value = "/add.json")
    public void addBook(@RequestBody Book book){
        bookRepository.save(book);
    }

    @GetMapping("/fetchAll")
    public List<Book> fetchAll (){
        return bookRepository.findAll();
    }
}
