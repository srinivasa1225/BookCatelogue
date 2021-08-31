package au.com.mylibrary.bookcatalogue.service;

import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.ADD_BOOK_SUCCESS_CODE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.ADD_BOOK_SUCCESS_CODE_MESSAGE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.BOOK_ALREADY_EXIST_CODE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.BOOK_ALREADY_EXIST_CODE_MESSAGE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.BOOK_NOT_FOUND_CODE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.BOOK_NOT_FOUND_CODE_MESSAGE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.DELETE_BOOK_SUCCESS_CODE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.DELETE_BOOK_SUCCESS_CODE_MESSAGE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.UPDATE_BOOK_SUCCESS_CODE;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.UPDATE_BOOK_SUCCESS_CODE_MESSAGE;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.com.mylibrary.bookcatalogue.beans.Book;
import au.com.mylibrary.bookcatalogue.beans.StatusMessage;
import au.com.mylibrary.bookcatalogue.entity.BookEntity;
import au.com.mylibrary.bookcatalogue.repository.BookCatalogueRepository;
import au.com.mylibrary.bookcatalogue.utils.CommonUtils;

/**
 * This class provides the implementation for BookCatalogueService interface methods tto handle business logic
 */

@Service
public class BookCatalogueServiceImpl implements BookCatalogueService{

    @Autowired
    BookCatalogueRepository bookCatalogueRepository;

    @Autowired
    CommonUtils commonUtils;

    private BiFunction<String,Book,BookEntity> bookToBookEntity =((isbn,book) ->{
        String authors = book.getAuthors().stream().map(author->author.getAuthorName()).collect(
            Collectors.joining(", "));

        return new BookEntity(isbn,book.getTitle(),authors,book.getPublisingDate());
    });


    @Override
    public StatusMessage addBook(Book book) {

        Optional<BookEntity> bookDetails = bookCatalogueRepository.findById(book.getIsbn());

        if(bookDetails.isPresent()){
            commonUtils.throwBookCatalogException(BOOK_ALREADY_EXIST_CODE,BOOK_ALREADY_EXIST_CODE_MESSAGE);
        }

        bookCatalogueRepository.save(bookToBookEntity.apply(book.getIsbn(),book));

        return commonUtils.getMessage(ADD_BOOK_SUCCESS_CODE,ADD_BOOK_SUCCESS_CODE_MESSAGE);
    }

    @Override
    public StatusMessage updateBook(String isbn, Book book) {

        Optional<BookEntity> bookDetails = bookCatalogueRepository.findById(isbn);

        if(bookDetails.isPresent()){
            commonUtils.throwBookCatalogException(BOOK_NOT_FOUND_CODE,BOOK_NOT_FOUND_CODE_MESSAGE);
        }


        if(bookDetails.isPresent()){

            bookDetails.get().setTitle(book.getTitle());

            bookDetails.get().setTitle(book.getAuthors().stream().map(author -> author.getAuthorName()).collect(Collectors.joining(", ")));
            bookDetails.get().setPublicationDate(book.getPublisingDate());
        }

        bookCatalogueRepository.save(bookDetails.get());

        return commonUtils.getMessage(UPDATE_BOOK_SUCCESS_CODE,UPDATE_BOOK_SUCCESS_CODE_MESSAGE);
    }

    @Override
    public BookEntity getBookByIsbn(String isbn) {

        return bookCatalogueRepository.findById(isbn).orElseThrow(()->commonUtils.bookCatalogueException(BOOK_NOT_FOUND_CODE,BOOK_NOT_FOUND_CODE_MESSAGE));

    }

    @Override
    public List<BookEntity> getBooksByTitle(String title) {

        List<BookEntity> bookDetails = bookCatalogueRepository.findByAuthors(title);

        if(bookDetails!=null && bookDetails.isEmpty()){
            commonUtils.throwBookCatalogException(BOOK_NOT_FOUND_CODE,BOOK_NOT_FOUND_CODE_MESSAGE);
        }


        return bookDetails;
    }

    @Override
    public List<BookEntity> getBooksByAuthor(String author) {

        List<BookEntity> bookDetails = bookCatalogueRepository.findByAuthors(author);

        if(bookDetails!=null && bookDetails.isEmpty()){
            commonUtils.throwBookCatalogException(BOOK_NOT_FOUND_CODE,BOOK_NOT_FOUND_CODE_MESSAGE);
        }

        return bookDetails;
    }

    @Override
    public StatusMessage deleteBook(String isbn) {

        Optional<BookEntity> bookDetails = bookCatalogueRepository.findById(isbn);

        if(bookDetails.isPresent()){
            commonUtils.throwBookCatalogException(BOOK_NOT_FOUND_CODE,BOOK_NOT_FOUND_CODE_MESSAGE);
        }

        bookCatalogueRepository.deleteById(isbn);
        return commonUtils.getMessage(DELETE_BOOK_SUCCESS_CODE,DELETE_BOOK_SUCCESS_CODE_MESSAGE);
    }
}
