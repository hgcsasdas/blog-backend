package hgc.backendblog.blog.Responses;

public class CommentCUDResponse {

    private boolean done;
    private String message;

    // Constructor, getters y setters

    public CommentCUDResponse() {
    }

    public CommentCUDResponse(boolean done, String message) {
        this.done = done;
        this.message = message;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
