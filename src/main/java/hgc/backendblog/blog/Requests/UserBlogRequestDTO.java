package hgc.backendblog.blog.Requests;

public class UserBlogRequestDTO {
	private String username;
	private String blogId;

	public UserBlogRequestDTO() {
	}

	public UserBlogRequestDTO(String username, String blogId) {
		this.username = username;
		this.blogId = blogId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getBlogId() {
		return blogId;
	}

	public void setBlogId(String blogId) {
		this.blogId = blogId;
	}
}
