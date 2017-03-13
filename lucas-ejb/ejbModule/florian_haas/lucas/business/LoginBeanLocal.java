package florian_haas.lucas.business;

import java.util.List;

import javax.ejb.Local;
import javax.validation.constraints.NotNull;

import org.apache.shiro.subject.Subject;

import florian_haas.lucas.model.*;
import florian_haas.lucas.model.validation.*;
import florian_haas.lucas.util.validation.NotBlankString;

@Local
public interface LoginBeanLocal {

	public Long newLoginUser(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public Long newLoginUser(@ValidEntityId(entityClass = User.class) Long user, @ValidUnhashedPassword char[] password);

	public void login(@NotNull @NotBlankString String username, @ValidUnhashedPassword char[] password);

	public void logout();

	public Boolean changePassword(@ValidEntityId(entityClass = LoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] oldPassword,
			@ValidUnhashedPassword char[] newPassword);

	public Boolean newPassword(@ValidEntityId(entityClass = LoginUser.class) Long loginUserId, @ValidUnhashedPassword char[] newPassword);

	public Subject getSubject();

	public LoginUser findLoginUserById(@ValidEntityId(entityClass = LoginUser.class) Long id);

	public Boolean addLoginUserRole(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public Boolean removeLoginUserRoleFromUser(@ValidEntityId(entityClass = LoginUser.class) Long userId,
			@ValidEntityId(entityClass = LoginUserRole.class) Long roleId);

	public List<LoginUser> findAllLoginUsers();

	public String getDBUsername(@ValidEntityId(entityClass = LoginUser.class) Long userId);
}
