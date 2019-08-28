package cn.edu.guet.users;

import cn.edu.guet.roles.Roles;

public class Users {

	//实际存在的个体 实体
	private String usersId;
	private String username;
	private String password;
	private String rolesId;
	private Roles role;
	
	public String getUsersId() {
		return usersId;
	}
	public void setUsersId(String usersId) {
		this.usersId = usersId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRolesId() {
		return rolesId;
	}
	public void setRolesId(String rolesId) {
		this.rolesId = rolesId;
	}
	public Roles getRole() {
		return role;
	}
	public void setRole(Roles role) {
		this.role = role;
	}
	
	
}