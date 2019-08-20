package cn.edu.guet.service.impl;

import java.util.List;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.impl.PermissionDaoImpl;
import cn.edu.guet.service.IPermissionService;

public class PermissionServiceImpl implements IPermissionService {

	IPermissionDao permissionDao;
	public PermissionServiceImpl(){
		permissionDao=new PermissionDaoImpl();
	}
	@Override
	public List<Permission> getAllPermission() {
		return permissionDao.getAll();
	}
	@Override
	public List<Permission> getPermissionByRoleId(String roleId) {
		return permissionDao.getPermissionByRoleId(roleId);
	}
	@Override
	public PageModel<Permission> getAll(int currentPage) {
		return permissionDao.selectAll(currentPage);
	}
	@Override
	public void savePermission(Permission p) {
		permissionDao.save(p);
	}
}