package library.demo;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;

@Entity(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    private int id;

    @Column
    String title;

    @Column
    String description;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name="author_id", nullable = false)
    private Author author;


    @Column
    int copies;


    public Book(String title, String description, Author author, int copies) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.copies = copies;
    }

    public Book() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public int getCopies() {
        return copies;
    }

    public void setCopies(int copies) {
        this.copies = copies;
    }
}
