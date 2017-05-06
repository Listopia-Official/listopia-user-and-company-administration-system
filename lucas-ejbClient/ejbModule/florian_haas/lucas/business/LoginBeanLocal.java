package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.apache.shiro.subject.Subject;
import org.hibernate.validator.constraints.NotBlank;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.validation.*;

@Local
public interface LoginBeanLocal {

	public static final String USERNAME_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueUsername";
	public static final String USER_NOT_UNIQUE_EXCEPTION_MARKER = "notUniqueUser";

	public Long newLoginUser(@NotBlank String username, @ValidUnhashedPassword char[] password,
			@NotNull List<@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long> userRoleIds);

	public Long newLoginUser(@ValidEntityId(entityClass = ReadOnlyUser.class) Long user, @ValidUnhashedPassword char[] password,
			@NotNull List<@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long> userRoleIds);

	public void login(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public void logout();

	public void changePassword(@ValidUnhashedPassword char[] oldPassword, @ValidUnhashedPassword char[] newPassword);

	public void newPassword(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] newPassword);

	public Subject getSubject();

	public ReadOnlyLoginUser findLoginUserById(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long id);

	public ReadOnlyLoginUser findLoginUserByUsername(@NotBlank String username);

	public List<? extends ReadOnlyLoginUser> findLoginUsers(@NotNull Long id, @NotNull String username, Long userId,
			List<@TypeNotNull @TypeMin(0) Long> roleIds, @NotNull Boolean useId, @NotNull Boolean useUsername, @NotNull Boolean useUserId,
			@NotNull Boolean useRoleIds, @QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator usernameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator roleIdsComparator);

	public Boolean changeUsername(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long id, @NotBlank String username);

	public Boolean setUITheme(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long id, @ValidUITheme @NotNull String uiTheme);

	public Boolean addLoginUserRoleToUser(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId);

	public Boolean removeLoginUserRoleFromUser(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long userId,
			@ValidEntityId(entityClass = ReadOnlyLoginUserRole.class) Long roleId);

	public List<? extends ReadOnlyLoginUser> findAllLoginUsers();

	public List<? extends ReadOnlyLoginUserRole> getLoginUserRoles(@ValidEntityId(entityClass = ReadOnlyLoginUser.class) Long userId);

}
