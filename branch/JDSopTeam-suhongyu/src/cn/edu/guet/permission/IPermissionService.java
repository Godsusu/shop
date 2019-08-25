package cn.edu.guet.permission;

import java.util.List;

import org.lanqiao.util.PageModel;

public interface IPermissionService {
	List<Permission> getAllPermission();
	List<Permission> getPermissionById(String roleId);
	PageModel<Permission> getAll(int currentPage);
	void savePermission(Permission p);
	void deletePermission(String permissionId);
	void updatePermission(Permission p);
}
