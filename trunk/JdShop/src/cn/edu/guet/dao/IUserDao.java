package cn.edu.guet.dao;

import java.util.Set;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Users;

public interface IUserDao extends IBaseDao<Users> {
	Users login(String username,String password);
	Set<Permission> getPermission(String username);
	void saveGrant(String userId, String roleId);
}