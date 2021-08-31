package au.com.mylibrary.bookcatalogue.controller;

import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.*;
import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.INVALID_DATE_MESSAGE;

import java.util.List;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import au.com.mylibrary.bookcatalogue.beans.Book;
import au.com.mylibrary.bookcatalogue.beans.StatusMessage;
import au.com.mylibrary.bookcatalogue.entity.BookEntity;
import au.com.mylibrary.bookcatalogue.exception.InvalidDateException;
import au.com.mylibrary.bookcatalogue.producer.BookCatalogueKafkaProducer;
import au.com.mylibrary.bookcatalogue.service.BookCatalogueService;
import au.com.mylibrary.bookcatalogue.utils.BookCatalogueUtil;
import au.com.mylibrary.bookcatalogue.utils.CommonUtils;
import au.com.mylibrary.bookcatalogue.wrapper.BookWrapper;

/**
 * This class has rest end poinds for cusrd operations for book catalogue
 */

@RestController
@RequestMapping("/book")
public class BookCatalogueController {

    private static final Logger logger = LoggerFactory.getLogger(BookCatalogueController.class);

    @Autowired
    BookCatalogueService bookCatalogueService;

    @Autowired
    CommonUtils commonUtils;

    @Autowired
    BookCatalogueKafkaProducer bookCatalogueKafkaProducer;

    private String topic = "bookcatalogue-topic";


    @PostMapping(value = "/v1/addBook" ,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusMessage> addBook(@Valid@RequestBody Book book) {

        logger.info("Entering addBook() Method");

        try {
            if (!BookCatalogueUtil.validateDate(book.getPublisingDate())) {
                throw new InvalidDateException(INVALID_DATE, INVALID_DATE_MESSAGE);
            }

            StatusMessage statusMessage = bookCatalogueService.addBook(book);
            bookCatalogueKafkaProducer.send(topic, ADD_BOOK_SUCCESS_CODE_MESSAGE);

            return ResponseEntity.status(HttpStatus.CREATED).body(statusMessage);

        } catch (Exception exception) {
            logger.error("Error in addMethod {}",exception.getMessage());
            throw exception;
        }

    }

    @PostMapping(value = "/v1/updateBook/{isbn}" ,consumes=MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusMessage> updateBook(@PathVariable String isbn , @RequestBody Book book) {

        logger.info("Entering updateBook() Method");

        try {
            if (!BookCatalogueUtil.validateDate(book.getPublisingDate())) {
                throw new InvalidDateException(INVALID_DATE, INVALID_DATE_MESSAGE);
            }

            StatusMessage statusMessage = bookCatalogueService.updateBook(isbn,book);
            bookCatalogueKafkaProducer.send(topic, UPDATE_BOOK_SUCCESS_CODE_MESSAGE);

            return ResponseEntity.status(HttpStatus.OK).body(statusMessage);

        } catch (Exception exception) {
            logger.error("Error in updateeMethod {}",exception.getMessage());
            throw exception;
        }

    }

    @DeleteMapping(value = "/v1/deleteBook/{isbn}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StatusMessage> deleteBook(@PathVariable String isbn) {

        logger.info("Entering deleteBook() Method");

        try {
            StatusMessage statusMessage = bookCatalogueService.deleteBook(isbn);
            bookCatalogueKafkaProducer.send(topic, UPDATE_BOOK_SUCCESS_CODE_MESSAGE);

            return ResponseEntity.status(HttpStatus.OK).body(statusMessage);

        } catch (Exception exception) {
            logger.error("Error in updateeMethod {}",exception.getMessage());
            throw exception;
        }

    }

    @GetMapping(value = "/v1/getBookByIsbn/{isbn}" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBookByIsbn(@PathVariable String isbn) {

        logger.info("Entering getBookByIsbn() Method");

        try {
            if (!BookCatalogueUtil.validateIsbn(isbn)) {
                commonUtils.throwBookCatalogException(INVALID_ISBN,INVALID_ISBN_MESSAGE);
            }

            BookEntity bookEntitty = bookCatalogueService.getBookByIsbn(isbn);
            BookWrapper bookWrapper = new BookWrapper();
            bookWrapper.setBookEntity(bookEntitty);



            bookCatalogueKafkaProducer.send(topic, RETRIEVE_BOOK_KAFKA_MESSAGE);

            return ResponseEntity.status(HttpStatus.OK).body(BookCatalogueUtil.toResponse(bookWrapper));

        } catch (Exception exception) {
            logger.error("Error in updateeMethod {}",exception.getMessage());
            throw exception;
        }

    }

    @GetMapping(value = "/v1/getBookByAuthor" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBookByAuthor(@RequestParam("author") String author) {

        logger.info("Entering getBookByAuthor() Method");

        try {
            if (!BookCatalogueUtil.validateString(author)) {
                commonUtils.throwBookCatalogException(INVALID_AUTHOR,INVALID_AUTHOR_MESSAGE);
            }

            List<BookEntity> books = bookCatalogueService.getBooksByAuthor(author);
            BookWrapper bookWrapper = new BookWrapper();
            bookWrapper.setBooks(books);



            bookCatalogueKafkaProducer.send(topic, RETRIEVE_BOOK_KAFKA_MESSAGE);

            return ResponseEntity.status(HttpStatus.OK).body(BookCatalogueUtil.toResponse(bookWrapper));

        } catch (Exception exception) {
            logger.error("Error in updateeMethod {}",exception.getMessage());
            throw exception;
        }

    }


    @GetMapping(value = "/v1/getBookByTitle" ,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getBookByTitle(@RequestParam("titlee") String title) {

        logger.info("Entering getBookByTitle() Method");

        try {
            if (!BookCatalogueUtil.validateString(title)) {
                commonUtils.throwBookCatalogException(INVALID_TITLE,INVALID_TITLE_MESSAGE);
            }

            List<BookEntity> books = bookCatalogueService.getBooksByTitle(title);
            BookWrapper bookWrapper = new BookWrapper();
            bookWrapper.setBooks(books);



            bookCatalogueKafkaProducer.send(topic, RETRIEVE_BOOK_KAFKA_MESSAGE);

            return ResponseEntity.status(HttpStatus.OK).body(BookCatalogueUtil.toResponse(bookWrapper));

        } catch (Exception exception) {
            logger.error("Error in updateeMethod  {}",exception.getMessage());
            throw exception;
        }

    }

}
