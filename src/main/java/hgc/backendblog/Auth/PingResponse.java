package hgc.backendblog.Auth;

import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class PingResponse {
	private String token;
	private boolean refresh = false;
	private boolean logout = false;

	public PingResponse() {
		super();
	}

	public PingResponse(String token, boolean refresh, boolean logout) {
		super();
		this.token = token;
		this.refresh = refresh;
		this.logout = logout;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	public boolean isLogout() {
		return logout;
	}

	public void setLogout(boolean logout) {
		this.logout = logout;
	}

}
