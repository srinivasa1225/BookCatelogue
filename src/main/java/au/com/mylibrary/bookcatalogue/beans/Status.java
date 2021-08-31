package au.com.mylibrary.bookcatalogue.beans;

import java.util.List;

/**
 * This class holds the list of message object information
 */
public class Status {

    private List<Message> message = null;

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }



}
