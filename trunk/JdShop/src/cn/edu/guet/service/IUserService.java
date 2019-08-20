package cn.edu.guet.service;

import java.util.List;
import java.util.Set;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Users;

public interface IUserService {

	void saveUser(Users user);
	Users login(String username,String password);
	Set<Permission> getPermission(String username);
	PageModel<Users> getAllUser(int currentPage);
	void saveGrant(String userId,String roleId);
	/**
	 * 
	 * @param userId 根据userId来删除记录
	 * @return 返回1或0；1表示删除成功、0表示删除失败
	 */
	int deleteUser(String userId);
}