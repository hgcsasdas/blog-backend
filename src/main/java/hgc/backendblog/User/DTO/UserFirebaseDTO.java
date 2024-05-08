package hgc.backendblog.User.DTO;

import java.util.List;

public class UserFirebaseDTO {
	private String username;
	private List<String> blogs;
	private List<String> likedBlogs;
	private List<String> disLikedBlogs;

	public UserFirebaseDTO() {
		super();
	}

	public UserFirebaseDTO(String username, List<String> blogs, List<String> likedBlogs, List<String> disLikedBlogs) {
		super();
		this.username = username;
		this.blogs = blogs;
		this.likedBlogs = likedBlogs;
		this.disLikedBlogs = disLikedBlogs;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<String> getBlogs() {
		return blogs;
	}

	public void setBlogs(List<String> blogs) {
		this.blogs = blogs;
	}

	public List<String> getLikedBlogs() {
		return likedBlogs;
	}

	public void setLikedBlogs(List<String> likedBlogs) {
		this.likedBlogs = likedBlogs;
	}

	public List<String> getDisLikedBlogs() {
		return disLikedBlogs;
	}

	public void setDisLikedBlogs(List<String> disLikedBlogs) {
		this.disLikedBlogs = disLikedBlogs;
	}

}
