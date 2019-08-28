package cn.edu.guet.users;

import java.sql.SQLException;
import java.util.Set;

import cn.edu.guet.base.IBaseDao;
import cn.edu.guet.permission.Permission;


public interface IUserDao extends IBaseDao<Users> {
	Users login(String username,String password) throws SQLException;
	Set<Permission> getPermission(String username) throws SQLException;
	void grantUser(String userId,String roleId) throws SQLException;
}