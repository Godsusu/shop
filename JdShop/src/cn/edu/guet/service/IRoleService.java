package cn.edu.guet.service;

import java.util.List;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;

public interface IRoleService {

	List<Roles> getAll();
	void saveGrant(String roleId,String permissionIds[]);
}