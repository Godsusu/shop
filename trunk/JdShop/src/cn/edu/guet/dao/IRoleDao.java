package cn.edu.guet.dao;

import cn.edu.guet.bean.Roles;

/**
 * 
 * @author liwei
 *
 */
public interface IRoleDao extends IBaseDao<Roles>{

	/**
	 * ��ɫ��Ȩ
	 * @param roleId ����Ȩ�Ľ�ɫID
	 * @param permissionIds ���������ɫ��Ȩ��ID
	 */
	void saveGrant(String roleId,String permissionIds[]);
}