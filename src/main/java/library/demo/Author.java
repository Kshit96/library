package library.demo;

import javax.persistence.*;

@Entity (name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_seq")
    private int id;

    @Column
    String name;

    @Column
    String bio;

    public Author(String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public Author() {
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
}


