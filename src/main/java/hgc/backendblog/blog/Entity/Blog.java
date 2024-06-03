package hgc.backendblog.blog.Entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Blog {

    private String id;
    private String title;
    private String author;
    private String content;

    private Map<String, Comment> comments;
    private int likes;
    private int dislikes;
    private Date date;
	public Blog() {
		super();
	}
	public Blog(String id, String title, String author, String content, Map<String, Comment> comments, int likes,
			int dislikes, Date date) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.content = content;
		this.comments = comments;
		this.likes = likes;
		this.dislikes = dislikes;
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public Map<String, Comment> getComments() {
		return comments;
	}
	public void setComments(Map<String, Comment> comments) {
		this.comments = comments;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public int getDislikes() {
		return dislikes;
	}
	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
    
    
}
