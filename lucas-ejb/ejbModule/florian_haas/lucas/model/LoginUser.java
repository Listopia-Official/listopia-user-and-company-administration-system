package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.validation.DefaultLoginUserRequired;
import florian_haas.lucas.validation.TypeNotNull;

@Entity
@Table(name = "loginuser")
public class LoginUser extends EntityBase implements ReadOnlyLoginUser {

	private static final long serialVersionUID = -3076379041617188865L;

	@Column(name = "username", nullable = false, unique = true)
	@NotBlank
	private String username;

	@Column(name = "password", nullable = false)
	@NotBlank
	private String hashedPassword;

	@JoinColumn(unique = true)
	@OneToOne
	@Null(groups = DefaultLoginUserRequired.class)
	private User user;

	private String uiTheme = null;

	@OneToMany(cascade = {
			CascadeType.PERSIST, CascadeType.REFRESH })
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

	@Override
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getHashedPassword() {
		return this.hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	@Override
	public User getUser() {
		return this.user;
	}

	@Override
	public Set<ReadOnlyLoginUserRole> getRoles() {
		return Collections.unmodifiableSet(this.roles);
	}

	public Boolean addRole(LoginUserRole role) {
		return this.roles.add(role);
	}

	public Boolean removeRole(LoginUserRole role) {
		return this.roles.remove(role);
	}

	@Override
	public String getUiTheme() {
		return this.uiTheme;
	}

	public void setUiTheme(String uiTheme) {
		this.uiTheme = uiTheme;
	}
}
