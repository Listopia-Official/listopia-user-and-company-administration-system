package florian_haas.lucas.business;

import java.util.List;

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

	public Long newLoginUser(@NotBlank String username, @ValidUnhashedPassword char[] password,
			@NotNull List<@ValidEntityId(entityClass = LoginUserRole.class) Long> userRoleIds);

	public Long newLoginUser(@ValidEntityId(entityClass = User.class) Long user, @ValidUnhashedPassword char[] password,
			@NotNull List<@ValidEntityId(entityClass = LoginUserRole.class) Long> userRoleIds);

	public void login(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public void logout();

	public Boolean changePassword(@ValidUnhashedPassword char[] oldPassword, @ValidUnhashedPassword char[] newPassword);

	public Boolean newPassword(@ValidEntityId(entityClass = LoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] newPassword);

	public Subject getSubject();

	public LoginUser findLoginUserById(@ValidEntityId(entityClass = LoginUser.class) Long id);

	public LoginUser findLoginUserByUsername(@NotBlank String username);

	public List<LoginUser> findLoginUsers(@NotNull Long id, @NotNull String username, Long userId, List<@TypeNotNull @TypeMin(0) Long> roleIds,
			@NotNull Boolean useId, @NotNull Boolean useUsername, @NotNull Boolean useUserId, @NotNull Boolean useRoleIds,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator idComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.TEXT) EnumQueryComparator usernameComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.NUMERIC) EnumQueryComparator userIdComparator,
			@QueryComparator(category = EnumQueryComparatorCategory.LOGIC) EnumQueryComparator roleIdsComparator);

	public Boolean changeUsername(@ValidEntityId(entityClass = LoginUser.class) Long id, @NotBlank String username);

	public Boolean setUITheme(@ValidEntityId(entityClass = LoginUser.class) Long id, @ValidUITheme @NotNull String uiTheme);

	public Boolean addLoginUserRoleToUser(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public Boolean removeLoginUserRoleFromUser(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public List<LoginUser> findAllLoginUsers();

	public List<LoginUserRole> getLoginUserRoles(@ValidEntityId(entityClass = LoginUser.class) Long userId);

}
