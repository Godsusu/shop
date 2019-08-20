package cn.edu.guet.service.impl;

import java.util.List;

import cn.edu.guet.bean.Roles;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.impl.RoleDaoImpl;
import cn.edu.guet.service.IRoleService;

public class RoleServiceImpl implements IRoleService {

	IRoleDao roleDao;
	public RoleServiceImpl(){
		roleDao=new RoleDaoImpl();
	}
	@Override
	public List<Roles> getAll() {
		return roleDao.getAll();
	}
	@Override
	public void saveGrant(String roleId, String[] permissionIds) {
		roleDao.saveGrant(roleId, permissionIds);
	}
}