package cn.edu.guet.permission;

import java.sql.SQLException;
import java.util.List;

import cn.edu.guet.base.IBaseDao;


public interface IPermissionDao extends IBaseDao<Permission>{
	List<Permission> getPermissionById(String roleId) throws SQLException;
	void deletePermissionChild(String id) throws SQLException;
	List<Permission> getChildsById(String permissionId) throws SQLException;
}
