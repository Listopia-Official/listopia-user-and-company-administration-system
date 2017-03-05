package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.database.*;
import florian_haas.lucas.database.validation.QueryComparator;
import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.validation.*;

@Local
public interface LoginBeanLocal {

	public Long newLoginUser(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public Long newLoginUser(@ValidEntityId(entityClass = User.class) Long user, @ValidUnhashedPassword char[] password);

	public Long newLoginUserRole(@NotBlank String name, Set<@TypeNotNull String> permissions);

	public void login(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public void logout();

	public Boolean changePassword(@ValidEntityId(entityClass = LoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] oldPassword,
			@ValidUnhashedPassword char[] newPassword);

	public Boolean newPassword(@ValidEntityId(entityClass = LoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] newPassword);

	public Subject getSubject();

	public LoginUser findLoginUserById(@ValidEntityId(entityClass = LoginUser.class) Long id);

	public Boolean addLoginUserRole(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public Boolean removeLoginUserRole(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public Boolean setRoleName(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String name);

	public Boolean addPermission(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String permission);

	public Boolean removePermission(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId, @NotBlank String permission);

	public LoginUserRole findLoginUserRoleById(@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public List<LoginUserRole> findAllLoginUserRoles();

	public List<LoginUserRole> findLoginUserRoles(@NotNull Long id, @NotNull String name,
			@NotNull Set<@TypeNotNull @NotBlankString String> permissions, @NotNull Boolean useId, @NotNull Boolean useName,
			@NotNull Boolean usePermissions, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator nameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.ARRAY) EnumQueryComparator permissionsComparator);

	public List<LoginUser> findAllLoginUsers();

	public String getDBUsername(@ValidEntityId(entityClass = LoginUser.class) Long userId);
}
