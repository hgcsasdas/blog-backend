package hgc.backendblog.blog.Responses;

<<<<<<< HEAD
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
=======
import lombok.Data;

@Data
>>>>>>> 7c15b610c3a6838d467816a8c2437e29a4284690
public class BlogCUDResponse {

	private boolean done = false;
	private String message;
	private String token;
<<<<<<< HEAD
	
=======

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

>>>>>>> 7c15b610c3a6838d467816a8c2437e29a4284690
}
