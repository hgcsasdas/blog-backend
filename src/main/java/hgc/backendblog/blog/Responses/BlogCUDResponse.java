package hgc.backendblog.blog.Responses;

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
public class BlogCUDResponse {

	private boolean done = false;
	private String message;
	private String token;
	
}
