package florian_haas.lucas.model;

import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.util.validation.TypeNotNull;

@Entity
@Table(name = "loginroles")
public class LoginUserRole extends EntityBase {

	private static final long serialVersionUID = -539981772258580862L;

	@Column(name = "name", nullable = false, unique = true)
	@NotBlank
	private String name;

	@ElementCollection
	@CollectionTable(name = "loginpermissions")
	@NotNull
	private Set<@TypeNotNull String> permissions = new HashSet<>();

	LoginUserRole() {}

	public LoginUserRole(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getPermissions() {
		return Collections.unmodifiableSet(permissions);
	}

	public Boolean addPermission(String permission) {
		return permissions.add(permission);
	}

	public Boolean removePermission(String permission) {
		return permissions.remove(permission);
	}
}
