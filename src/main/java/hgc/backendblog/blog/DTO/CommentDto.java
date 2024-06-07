package hgc.backendblog.blog.DTO;

public class CommentDto {

    private String author;
    private String content;
	private String token;


    public CommentDto() {
    }

	public CommentDto(String author, String content, String token) {
		super();
		this.author = author;
		this.content = content;
		this.token = token;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
