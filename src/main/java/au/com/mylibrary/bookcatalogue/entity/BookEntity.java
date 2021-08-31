package au.com.mylibrary.bookcatalogue.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class old the fileds mapped tto DB table
 */
@Entity
@Table(name = "bookcatalogue")
public class BookEntity {

    @Id
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "authors")
    private String authors;

    @Column(name = "publicationDate")
    private String publicationDate;

    public BookEntity() {
    }

    public BookEntity(String isbn, String title, String authors, String publicationDate) {

        this.isbn = isbn;
        this.title = title;
        this.authors = authors;
        this.publicationDate = publicationDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

}
