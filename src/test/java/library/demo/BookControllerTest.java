package library.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    MockMvc app;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;


    @Test
    public void addBook_shouldAddBookInTheLibrary_whenGivenBookIsNotAddedInTheLibrary() throws Exception {
        Author author = new Author("JRR Tolkein", "Crazy guy");

        Author saveAuthor = authorRepository.save(author);

        Book book = new Book("LOTR", "Fantasy", saveAuthor, 10);
        final ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/add.json")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(book)))
                .andExpect(status().isOk());
    }

    @Test
    public void fetchAll_shouldfetchAllBooksFromTheLibrary_whenInvoked() throws Exception {
        Author author = new Author("JRR Tolkein", "Crazy guy");

        final ObjectMapper mapper = new ObjectMapper();

        Author savedAuthor = authorRepository.save(author);

        Book book1 = new Book("LOTR", "Fantasy", savedAuthor, 10);
        Book book2 = new Book("LOTR2", "Fantasy", savedAuthor, 15);
        bookRepository.save(book1);
        bookRepository.save(book2);

        List<Book> expectedListBooks= Arrays.asList(book1,book2);

        app.perform(get("/library/fetchAll"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(expectedListBooks)));
    }

}