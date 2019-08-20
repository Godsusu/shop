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
	 * @param userId ����userId��ɾ����¼
	 * @return ����1��0��1��ʾɾ���ɹ���0��ʾɾ��ʧ��
	 */
	int deleteUser(String userId);
}