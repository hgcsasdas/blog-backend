package hgc.backendblog.blog.Responses;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BlogCUDResponse {

	private boolean done = false;
	private String message;
	private String token;


	public BlogCUDResponse() {
		super();
	}

	public BlogCUDResponse(boolean done, String message, String token) {
		super();
		this.done = done;
		this.message = message;
		this.token = token;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
