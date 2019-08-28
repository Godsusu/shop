package cn.edu.guet.users;

import java.util.Set;

import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.permission.Permission;


public interface IUserService {
	//œÍœ∏…Ëº∆
	void saveUser(Users user) throws DaoException;
	Users login(String username,String password);
	Set<Permission> getPermission(String username);
	PageModel<Users> getAllUsers(int currentPage);
	void grantUser(String userId,String roleId) throws DaoException;
	void deleteUser(String userId) throws DaoException;
}
