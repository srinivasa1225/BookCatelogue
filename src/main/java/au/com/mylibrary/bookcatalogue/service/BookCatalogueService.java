package au.com.mylibrary.bookcatalogue.service;

import java.util.List;

import au.com.mylibrary.bookcatalogue.beans.Book;
import au.com.mylibrary.bookcatalogue.beans.StatusMessage;
import au.com.mylibrary.bookcatalogue.entity.BookEntity;

/**
 * This interface defines method for bookcatalogue.
 */
public interface BookCatalogueService {

    StatusMessage addBook(Book book);

    StatusMessage updateBook(String isbn, Book book);

    BookEntity getBookByIsbn(String isbn);

    List<BookEntity> getBooksByTitle(String title);

    List<BookEntity> getBooksByAuthor(String author);

    StatusMessage deleteBook(String isbn);
}
