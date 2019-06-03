package library.demo.lending;

import library.demo.book.Book;
import library.demo.customer.Customer;

import javax.persistence.*;

@Entity(name = "lendingRecord")
public class LendingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lending_seq")
    private int id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Column
    private State state;

    public LendingRecord(Customer customer, Book book, State state) {
        this.customer = customer;
        this.book = book;
        this.state = state;
    }

    public LendingRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
