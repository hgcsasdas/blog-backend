package hgc.backendblog.User.DTO;

import hgc.backendblog.User.Entitys.Plan;

public class UserDTO {

	private int id;
	private String firebaseId;
	private String email;
	private String apiToken;
	private Plan plan;
	private int blogEntries;
	private String username;

	public UserDTO() {
		super();
	}

	public UserDTO(int id, String firebaseId, String email, String apiToken, Plan plan, int blogEntries,
			String username) {
		super();
		this.id = id;
		this.firebaseId = firebaseId;
		this.email = email;
		this.apiToken = apiToken;
		this.plan = plan;
		this.blogEntries = blogEntries;
		this.username = username;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirebaseId() {
		return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
		this.firebaseId = firebaseId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public int getBlogEntries() {
		return blogEntries;
	}

	public void setBlogEntries(int blogEntries) {
		this.blogEntries = blogEntries;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
