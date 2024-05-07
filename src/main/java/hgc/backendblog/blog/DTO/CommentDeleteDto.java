package hgc.backendblog.blog.DTO;

public class CommentDeleteDto {
    private String author;
    private String blogId; 

    public CommentDeleteDto() {
        super();
    }

    public CommentDeleteDto(String author, String blogId) {
        super();
        this.author = author;
        this.blogId = blogId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
}
