package au.com.mylibrary.bookcatalogue.exception;

/**
 * Exception class for bookcatalogue.
 */
public class BookCatalogueException extends RuntimeException{

    private String code;

    public BookCatalogueException(String message){
        super(message);
    }

    public BookCatalogueException(String code, String message){
        super(message);
        this.code = code;
    }

    public BookCatalogueException(String code, String message, Throwable throwable){
        super(message,throwable);
        this.code = code;
    }

    public String getCode() {
        return code;
    }



}
