package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.model.validation.ValidEntityId;
import florian_haas.lucas.util.validation.*;

@Local
public interface LoginUserRoleBeanLocal {

	public Long newLoginUserRole(@NotBlank String name, Set<@TypeNotNull String> permissions);

	public Boolean removeLoginUserRole(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public Boolean setRoleName(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String name);

	public Boolean addPermission(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String permission);

	public Boolean removePermission(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String permission);

	public Set<String> getPermissions(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public LoginUserRole findById(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public List<LoginUserRole> findAll();

	public List<LoginUserRole> findLoginUserRoles(@NotNull Long id, @NotNull String name, Set<@TypeNotNull @NotBlankString String> permissions,
			@NotNull Boolean useId, @NotNull Boolean useName, @NotNull Boolean usePermissions,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator permissionsComparator);

}
