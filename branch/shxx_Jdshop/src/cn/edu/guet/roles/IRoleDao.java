package cn.edu.guet.roles;

import java.sql.SQLException;

import cn.edu.guet.base.IBaseDao;


public interface IRoleDao extends IBaseDao<Roles>{
	/**
	 * ����ɫ��Ȩ
	 * @param roleId
	 * @param permissionIds
	 */
	void saveGrant(String roleId,String permissionIds[]) throws SQLException;
}
