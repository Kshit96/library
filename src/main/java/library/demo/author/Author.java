package library.demo.author;

import library.demo.book.Book;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity (name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    private int id;

    @Column
    String name;

    @Column
    String bio;


    @OneToMany(mappedBy = "author")
    private Set<Book> books;


    public Author(String name, String bio) {
        this.name = name;
        this.bio = bio;
        this.books = new HashSet<>();
    }

    public Author() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void addBook(Book book) {
        this.books.add(book);
    }
}


