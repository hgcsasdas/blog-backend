package hgc.backendblog.User.Entitys;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username", "email", "firebase_id" }) })
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String firebaseId;

	@Column(nullable = false)
	private String username;

	@Column(nullable = false)
	private String email;

	private String password;
	private String apiToken;
	private Role role;
	private Plan plan;
	private int blogEntries;

	public User() {
		super();
	}

	public User(String firebaseId, String username, String email, String password) {
		super();
		this.firebaseId = firebaseId;
		this.username = username;
		this.email = email;
		this.password = password;

		this.apiToken = generateApiToken();
		this.role = Role.ROLE_USER;
		this.plan = Plan.FREE_PLAN;
		this.blogEntries = 10;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirebaseId() {
	    return firebaseId;
	}

	public void setFirebaseId(String firebaseId) {
	    this.firebaseId = firebaseId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApiToken() {
		return apiToken;
	}

	public void setApiToken(String apiToken) {
		this.apiToken = apiToken;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private String generateApiToken() {
		return UUID.randomUUID().toString();
	}
}
