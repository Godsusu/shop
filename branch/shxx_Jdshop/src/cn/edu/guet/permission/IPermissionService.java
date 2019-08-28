package cn.edu.guet.permission;

import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;

public interface IPermissionService {
	List<Permission> getAllPermission();
	List<Permission> getPermissionById(String roleId);
	PageModel<Permission> getAll(int currentPage);
	void savePermission(Permission p) throws DaoException;
	void deletePermission(String permissionId) throws DaoException;
	void updatePermission(Permission p) throws SQLException;
}
