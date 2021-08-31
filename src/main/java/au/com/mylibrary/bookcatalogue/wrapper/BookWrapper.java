package au.com.mylibrary.bookcatalogue.wrapper;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import au.com.mylibrary.bookcatalogue.entity.BookEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookWrapper {
    @JsonProperty(value = "book")
    private BookEntity bookEntity;

    @JsonProperty(value = "books")
    private List<BookEntity> books;

    public BookEntity getBookEntity() {
        return bookEntity;
    }

    public void setBookEntity(BookEntity bookEntity) {
        this.bookEntity = bookEntity;
    }

    public List<BookEntity> getBooks() {
        return books;
    }

    public void setBooks(List<BookEntity> books) {
        this.books = books;
    }
}
