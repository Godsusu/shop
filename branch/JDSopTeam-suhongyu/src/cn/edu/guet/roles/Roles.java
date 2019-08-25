package cn.edu.guet.roles;

import java.util.Set;

import cn.edu.guet.permission.Permission;

public class Roles {
	private String rolesId;
	private String roleName;
	private Set<Permission> permissions;//一个角色多个权限
	
	public Set<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(Set<Permission> permission) {
		this.permissions = permission;
	}
	public String getRolesId() {
		return rolesId;
	}
	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


}
