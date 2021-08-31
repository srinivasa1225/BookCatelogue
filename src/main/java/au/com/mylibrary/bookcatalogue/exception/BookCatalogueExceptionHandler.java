package au.com.mylibrary.bookcatalogue.exception;

import static au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants.*;
import static au.com.mylibrary.bookcatalogue.utils.BookCatalogueUtil.responseStatus;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import au.com.mylibrary.bookcatalogue.beans.StatusMessage;
import au.com.mylibrary.bookcatalogue.constant.BookCatalogueConstants;
import au.com.mylibrary.bookcatalogue.utils.BookCatalogueUtil;
import au.com.mylibrary.bookcatalogue.utils.CommonUtils;

/**
 * Exception handler for bookcatalogue
 */

@Order(1)
@RestControllerAdvice
public class BookCatalogueExceptionHandler {

    @Autowired Environment environment;

    @Autowired CommonUtils commonUtils;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception){

        List<String> details = new ArrayList<>();
        for(ObjectError error : exception.getBindingResult().getAllErrors()){
            details.add(error.getDefaultMessage());

        }
        StatusMessage  statusMessage = BookCatalogueUtil.requestValidationResponse(details);

        return new ResponseEntity<>(statusMessage,HttpStatus.BAD_REQUEST);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(InvalidFormatException exception){


        StatusMessage  statusMessage = commonUtils.getMessage(INVALID_DATE,INVALID_DATE_MESSAGE);

        return new ResponseEntity<>(statusMessage,HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BookCatalogueException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(BookCatalogueException exception){

        String code = exception.getCode();

        StatusMessage  statusMessage = responseStatus(code,exception.getMessage());
        if(environment.getProperty(BOOK_NOT_FOUND_CODE).equals(code)){
            return new ResponseEntity<>(statusMessage,HttpStatus.NOT_FOUND);
        }else if(environment.getProperty(BOOK_ALREADY_EXIST_CODE).equals(code)){
            return new ResponseEntity<>(statusMessage,HttpStatus.CONFLICT);
        }else{
            return new ResponseEntity<>(statusMessage,HttpStatus.CONFLICT);
        }

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidDateException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(InvalidDateException exception){

        String code = exception.getCode();

        StatusMessage  statusMessage = responseStatus(code,exception.getMessage());

            return new ResponseEntity<>(statusMessage,HttpStatus.BAD_REQUEST);

    }



    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(Exception exception,WebRequest webRequest){


        StatusMessage  statusMessage = null;

        if(exception instanceof HttpMessageNotReadableException || exception instanceof MissingServletRequestParameterException ){
              statusMessage = commonUtils.getMessage(INVALID_REQUEST,INVALID_REQUEST_MESSAGE);
            return new ResponseEntity<>(statusMessage,HttpStatus.BAD_REQUEST);
        }else{
            statusMessage = commonUtils.getMessage(TECH_ISSUE_CODE,TECH_ISSUE_MESSAGE);
            return new ResponseEntity<>(statusMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

}
