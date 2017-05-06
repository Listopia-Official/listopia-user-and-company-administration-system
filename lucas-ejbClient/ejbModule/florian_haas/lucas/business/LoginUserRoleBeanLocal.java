package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.ReadOnlyLoginUserRole;
import florian_haas.lucas.validation.*;

@Local
public interface LoginUserRoleBeanLocal {

	public Long newLoginUserRole(@NotBlank String name, Set<@TypeNotNull String> permissions);

	public Boolean removeLoginUserRole(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId);

	public Boolean setRoleName(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId, @NotBlank String name);

	public Boolean addPermission(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId, @NotBlank String permission);

	public Boolean removePermission(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId, @NotBlank String permission);

	public Set<String> getPermissions(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId);

	public ReadOnlyLoginUserRole findById(@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId);

	public List<? extends ReadOnlyLoginUserRole> findAll();

	public List<? extends ReadOnlyLoginUserRole> findLoginUserRoles(@NotNull Long id, @NotNull String name,
			Set<@TypeNotNull @NotBlankString String> permissions, @NotNull Boolean useId, @NotNull Boolean useName, @NotNull Boolean usePermissions,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator permissionsComparator);

}
