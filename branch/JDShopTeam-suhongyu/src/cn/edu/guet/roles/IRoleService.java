package cn.edu.guet.roles;

import java.util.List;

import cn.edu.guet.exception.DaoException;


public interface IRoleService {
	List<Roles> getAllRole();
	void saveGrant(String roleId,String permissionIds[]) throws DaoException;
}
