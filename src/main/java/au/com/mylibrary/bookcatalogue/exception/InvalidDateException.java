package au.com.mylibrary.bookcatalogue.exception;

public class InvalidDateException extends RuntimeException{

    private String code;

    public InvalidDateException(String message){
        super(message);
    }

    public InvalidDateException(String code, String message){
        super(message);
        this.code = code;
    }

    public InvalidDateException(String code, String message, Throwable throwable){
        super(message,throwable);
        this.code = code;
    }

    public String getCode() {
        return code;
    }



}