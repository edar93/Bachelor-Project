package vsb.cec0094.bachelorProject.models;

public class Message {

    private String text;

    private String author;

    public Message() {
    }

    public Message(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}