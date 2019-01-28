package ua.javaee.springreact.web.bean;

public class Greetings {

    public Greetings(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
