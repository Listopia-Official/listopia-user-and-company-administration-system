package florian_haas.lucas.business;

import static florian_haas.lucas.security.EnumPermission.*;

import java.util.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.model.*;
import florian_haas.lucas.persistence.*;
import florian_haas.lucas.security.*;

@Stateless
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginUserRoleBean implements LoginUserRoleBeanLocal {

	@Inject
	@JPADAO
	private LoginUserRoleDAO loginUserRoleDao;

	@Override
	@RequiresPermissions(LOGIN_ROLE_CREATE)
	public Long newLoginUserRole(String name, Set<String> permissions) {
		checkIsNameUnique(name);
		LoginUserRole role = new LoginUserRole(name);
		if (permissions != null) {
			permissions.forEach(permission -> {
				role.addPermission(permission);
			});
		}
		loginUserRoleDao.persist(role);
		return role.getId();
	}

	private void checkIsNameUnique(String name) {
		if (!loginUserRoleDao.isNameUnique(name)) throw new LucasException("The name of the login user role is already used");
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_SET_NAME)
	public Boolean setRoleName(Long roleId, String name) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		if (!role.getName().equals(name)) {
			checkIsNameUnique(name);
			role.setName(name);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_ADD_PERMISSION)
	public Boolean addPermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.addPermission(permission);
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_REMOVE_PERMISSION)
	public Boolean removePermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.removePermission(permission);
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_FIND_BY_ID)
	public LoginUserRole findById(Long roleId) {
		return loginUserRoleDao.findById(roleId);
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_FIND_ALL)
	public List<? extends ReadOnlyLoginUserRole> findAll() {
		return loginUserRoleDao.findAll();
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_FIND_DYNAMIC)
	public List<? extends ReadOnlyLoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Boolean useId, Boolean useName,
			Boolean usePermissions, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator permissionsComparator) {
		return loginUserRoleDao.findLoginUserRoles(id, name, permissions, useId, useName, usePermissions, idComparator, nameComparator,
				permissionsComparator);
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_GET_PERMISSIONS)
	public Set<String> getPermissions(Long roleId) {
		return loginUserRoleDao.findById(roleId).getPermissions();
	}

	@Override
	@RequiresPermissions(LOGIN_ROLE_REMOVE)
	public Boolean removeLoginUserRole(Long roleId) {
		if (!loginUserRoleDao.isUsed(roleId)) {
			LoginUserRole role = loginUserRoleDao.findById(roleId);
			loginUserRoleDao.delete(role);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
