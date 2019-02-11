package ua.javaee.springreact.web.util.error;

/**
 * Created by kleba on 11.02.2019.
 */
public class Error {
    private String message;
    private String type;

    public Error() {
    }

    ;

    public Error(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
