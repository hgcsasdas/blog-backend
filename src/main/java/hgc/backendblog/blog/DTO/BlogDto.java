package hgc.backendblog.blog.DTO;

public class BlogDto {

	private String title;
	private String author;
	private String content;
	private String token;

	public BlogDto() {
	}

	public BlogDto(String title, String author, String content, String token) {
		super();
		this.title = title;
		this.author = author;
		this.content = content;
		this.token = token;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
