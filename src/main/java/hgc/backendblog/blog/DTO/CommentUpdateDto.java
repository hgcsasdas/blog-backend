package hgc.backendblog.blog.DTO;

public class CommentUpdateDto {
    private String author;
    private String blogId;
    private String content;
	private String token;

    public CommentUpdateDto() {
        super();
    }

	public CommentUpdateDto(String author, String blogId, String content, String token) {
		super();
		this.author = author;
		this.blogId = blogId;
		this.content = content;
		this.token = token;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	

}
