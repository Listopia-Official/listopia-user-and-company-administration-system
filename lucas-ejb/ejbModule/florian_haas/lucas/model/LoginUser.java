package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import florian_haas.lucas.util.validation.*;

@Entity
@Table(name = "loginuser")
public class LoginUser extends EntityBase {

	private static final long serialVersionUID = -3076379041617188865L;

	@Column(name = "username", nullable = false, unique = true)
	@NotBlankString
	@NotNull
	private String username;

	@Column(name = "password", nullable = false)
	@NotBlankString
	@NotNull
	private String hashedPassword;

	@OneToOne
	private User user;

	@OneToMany
	@NotNull
	private Set<@TypeNotNull LoginUserRole> roles = new HashSet<>();

	LoginUser() {}

	public LoginUser(String username, String hashedPassword) {
		this.username = username;
		this.hashedPassword = hashedPassword;
	}

	public LoginUser(String username, String hashedPassword, User user) {
		this(username, hashedPassword);
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public User getUser() {
		return user;
	}

	public Set<LoginUserRole> getRoles() {
		return Collections.unmodifiableSet(roles);
	}

	public Boolean addRole(LoginUserRole role) {
		return roles.add(role);
	}

	public Boolean removeRole(LoginUserRole role) {
		return roles.remove(role);
	}
}
