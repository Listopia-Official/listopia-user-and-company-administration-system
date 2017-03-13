package florian_haas.lucas.business;

import java.util.*;

import javax.ejb.*;
import javax.inject.Inject;
import javax.validation.executable.*;

import florian_haas.lucas.database.*;
import florian_haas.lucas.model.LoginUserRole;
import florian_haas.lucas.security.Secured;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@ValidateOnExecution(type = ExecutableType.IMPLICIT)
@Secured
public class LoginUserRoleBean implements LoginUserRoleBeanLocal {

	@Inject
	@JPADAO
	private LoginUserRoleDAO loginUserRoleDao;

	@Override
	public Long newLoginUserRole(String name, Set<String> permissions) {
		LoginUserRole role = new LoginUserRole(name);
		if (permissions != null) {
			permissions.forEach(permission -> {
				role.addPermission(permission);
			});
		}
		loginUserRoleDao.persist(role);
		return role.getId();
	}

	@Override
	public Boolean setRoleName(Long roleId, String name) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		if (!role.getName().equals(name)) {
			role.setName(name);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public Boolean addPermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.addPermission(permission);
	}

	@Override
	public Boolean removePermission(Long roleId, String permission) {
		LoginUserRole role = loginUserRoleDao.findById(roleId);
		return role.removePermission(permission);
	}

	@Override
	public LoginUserRole findLoginUserRoleById(Long roleId) {
		return loginUserRoleDao.findById(roleId);
	}

	@Override
	public List<LoginUserRole> findAllLoginUserRoles() {
		return loginUserRoleDao.findAll();
	}

	@Override
	public List<LoginUserRole> findLoginUserRoles(Long id, String name, Set<String> permissions, Boolean useId, Boolean useName,
			Boolean usePermissions, EnumQueryComparator idComparator, EnumQueryComparator nameComparator, EnumQueryComparator permissionsComparator) {
		return loginUserRoleDao.findLoginUserRoles(id, name, permissions, useId, useName, usePermissions, idComparator, nameComparator,
				permissionsComparator);
	}

	@Override
	public Set<String> getPermissions(Long roleId) {
		return loginUserRoleDao.findById(roleId).getPermissions();
	}

	@Override
	public Boolean removeLoginUserRole(Long roleId) {
		if (!loginUserRoleDao.isUsed(roleId)) {
			LoginUserRole role = loginUserRoleDao.findById(roleId);
			loginUserRoleDao.delete(role);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
