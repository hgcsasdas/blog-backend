package hgc.backendblog.Auth;

import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ResponseBody
public class AuthResponse {
	String token;
	boolean logged = false;

	public AuthResponse() {
		super();
	}

	public AuthResponse(String token, boolean logged) {
		super();
		this.token = token;
		this.logged = logged;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isLogged() {
		return logged;
	}

	public void setLogged(boolean logged) {
		this.logged = logged;
	}

}
