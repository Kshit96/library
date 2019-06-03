package library.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class BookControllerTest {

    private Author author;
    private Book book;

    @Autowired
    MockMvc app;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @After
    public void tearUp(){
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Before
    public void setup(){
        this.author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);
        this.book = new Book("LOTR", "Fantasy", author, 10);
    }

    @Test
    public void addBook_shouldAddBookInTheLibrary_whenGivenBookIsNotAddedInTheLibrary() throws Exception {

        final ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    public void fetchAll_shouldfetchAllBooksFromTheLibrary_whenInvoked() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();

        Book book1 = new Book("LOTR", "Fantasy", author, 10);
        Book book2 = new Book("LOTR2", "Fantasy", author, 15);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> expectedListBooks= Arrays.asList(book1,book2);

        app.perform(get("/library/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedListBooks)));
    }

}