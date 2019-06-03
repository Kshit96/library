package library.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import library.demo.author.Author;
import library.demo.author.AuthorRepository;
import library.demo.book.Book;
import library.demo.book.BookRepository;
import library.demo.customer.Customer;
import library.demo.customer.CustomerRepository;
import library.demo.lending.LendingRecord;
import library.demo.lending.LendingRepository;
import library.demo.lending.State;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
public class LendingControllerTest {

    @Autowired
    MockMvc app;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    LendingRepository lendingRepository;

    @After
    public void tearUp() {
        lendingRepository.deleteAll();
        customerRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
    }

    @Test
    public void checkOut_shouldCheckoutABookAndReduceNumberOfCopiesBy1_whenInvoked() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        customer1 = customerRepository.save(customer1);
        Author author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);
        Book book = new Book("LOTR", "Fantasy", author, 10);
        Book expectedBookRecord = new Book("LOTR", "Fantasy", author, 9);
        book = bookRepository.save(book);
        expectedBookRecord.setId(book.getId());
        LendingRecord lendingRecord = new LendingRecord(customer1, book, State.LENDED);
        ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().isOk());

        assertEquals(expectedBookRecord, bookRepository.findById(book.getId()).get());
    }

    @Test
    public void checkOut_shouldThrowException_whenBookToCheckoutHas0Copies() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        customer1 = customerRepository.save(customer1);

        Author author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);

        Book book = new Book("LOTR", "Fantasy", author, 0);
        book = bookRepository.save(book);

        LendingRecord lendingRecord = new LendingRecord(customer1, book, State.LENDED);
        ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void return_shouldReturnBook_whenBookIsReturned() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        customer1 = customerRepository.save(customer1);
        Author author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);
        Book book = new Book("LOTR", "Fantasy", author, 2);
        book = bookRepository.save(book);
        Book expectedBookRecord = new Book("LOTR", "Fantasy", author, 2);
        expectedBookRecord.setId(book.getId());
        LendingRecord lendingRecord = new LendingRecord(customer1, book, State.LENDED);
        ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().isOk());

        lendingRecord.setState(State.RETURNED);

        app.perform(post("/library/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().isOk());

        assertEquals(expectedBookRecord, bookRepository.findById(book.getId()).get());
    }

    @Test
    public void return_shouldReturnException_whenBookIsReturnedWithoutBeingBorrowed() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        customer1 = customerRepository.save(customer1);
        Author author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);
        Book book = new Book("LOTR", "Fantasy", author, 1);
        book = bookRepository.save(book);
        LendingRecord lendingRecord = new LendingRecord(customer1, book, State.RETURNED);
        ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void return_shouldThrowException_whenSameBookIsReturnedTwice() throws Exception {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("10/01/1980");
        java.sql.Date dateFormatted = new java.sql.Date(date.getTime());
        Customer customer1 = new Customer("Kshitiz", 23, 'M', dateFormatted);
        customer1 = customerRepository.save(customer1);
        Author author = new Author("JRR Tolkein", "Crazy guy");
        author = authorRepository.save(author);
        Book book = new Book("LOTR", "Fantasy", author, 2);
        book = bookRepository.save(book);
        Book expectedBookRecord = new Book("LOTR", "Fantasy", author, 2);
        expectedBookRecord.setId(book.getId());
        LendingRecord lendingRecord = new LendingRecord(customer1, book, State.LENDED);
        ObjectMapper mapper = new ObjectMapper();

        app.perform(post("/library/borrow")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)));

        lendingRecord.setState(State.RETURNED);

        app.perform(post("/library/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)));

        app.perform(post("/library/return")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(lendingRecord)))
                .andExpect(status().is4xxClientError());
    }

}
