package au.com.mylibrary.bookcatalogue.beans;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * This class holds book information
 */
public class Book {

    @NotEmpty(message = "Title is required.")
    private String title;

    @Valid
    @NotEmpty(message = "Author is required")
    private List<Author> authors;

    @NotNull(message = "ISBN is required.")
    @Pattern(regexp = "[0-9]{13}", message = "Invalid ISBN.")
    private String isbn;

    @NotNull(message = "Publication date is required.")
    private String publisingDate;

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublisingDate() {
        return publisingDate;
    }

    public void setPublisingDate(String publisingDate) {
        this.publisingDate = publisingDate;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
