package library.demo.book;

import library.demo.author.Author;

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


    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;


    @Column
    int availableCopies;


    public Book(String title, String description, Author author, int copies) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.availableCopies = copies;
    }

    public Book() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    @Override
    public boolean equals(Object object) {
        Book otherBook = (Book)object;

        if(this.id == otherBook.id && otherBook.availableCopies == this.availableCopies && otherBook.description.equals(this.description) && otherBook.title.equals(this.title)) {
            return true;
        }
        return false;
    }
}
