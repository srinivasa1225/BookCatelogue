package au.com.mylibrary.bookcatalogue.beans;

/**
 * This class holds the final response to be sent to consumer.
 */
public class Response {

    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
