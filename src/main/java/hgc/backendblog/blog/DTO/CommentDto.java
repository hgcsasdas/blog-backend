package hgc.backendblog.blog.DTO;

public class CommentDto {

    private String author;
    private String content;

    // Constructor, getters y setters

    public CommentDto() {
    }

    public CommentDto(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
