package cn.edu.guet.dao;

import java.util.List;

import cn.edu.guet.bean.Permission;

public interface IPermissionDao extends IBaseDao<Permission>{

	List<Permission> getPermissionByRoleId(String roleId);
}