package au.com.mylibrary.bookcatalogue.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


import au.com.mylibrary.bookcatalogue.beans.Message;
import au.com.mylibrary.bookcatalogue.beans.Response;
import au.com.mylibrary.bookcatalogue.beans.Status;
import au.com.mylibrary.bookcatalogue.beans.StatusMessage;

public class BookCatalogueUtil {

    public static final Pattern isbnPattern = Pattern.compile("[0-9]{13}");
    public static final Pattern stringPattern = Pattern.compile("^[A-Za-z, ]++$");




    public static StatusMessage responseStatus(String code,String messageDesc){
        List<Message> messageList = new ArrayList<>();

        StatusMessage statusMessage = new StatusMessage();
        Status status = new Status();
        Message message = new Message();
        message.setCode(code);
        message.setMessage(messageDesc);
        messageList.add(message);
        status.setMessage(messageList);
        statusMessage.setStatus(status);
        return statusMessage;
    }



    public static StatusMessage requestValidationResponse(List<String> errorMessages){
        List<Message> messageList = new ArrayList<>();

        StatusMessage statusMessage = new StatusMessage();
        Status status = new Status();
        for(String errorMessage : errorMessages) {
            Message message = new Message();
            message.setCode("100012");
            message.setMessage(errorMessage);
            messageList.add(message);
        }
        status.setMessage(messageList);
        statusMessage.setStatus(status);
        return statusMessage;
    }

    public static boolean isNullOrEmpty(String str){

        return (str!=null && !str.isEmpty()) ? false : true;

    }

    public  static Response toResponse(Object object){
        Response response = new Response();
        response.setData(object);
        return response;
    }

    public static boolean validateIsbn(String isbn){
        return isbnPattern.matcher(isbn).matches();
    }

    public static boolean validateString(String str){
        return stringPattern.matcher(str).matches();
    }

    public static boolean validateDate(String strDate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");

        try{
            simpleDateFormat.parse(strDate);
        }catch (ParseException ex){

            return false;
        }

        return true;
    }

}
