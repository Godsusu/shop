package cn.edu.guet.service;

import java.util.List;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Permission;

public interface IPermissionService {

	List<Permission> getAllPermission();
	List<Permission> getPermissionByRoleId(String roleId);
	PageModel<Permission> getAll(int currentPage);
	void savePermission(Permission p);
}