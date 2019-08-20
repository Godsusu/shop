package cn.edu.guet.test;

import java.util.UUID;

import org.junit.Test;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.impl.PermissionDaoImpl;
import cn.edu.guet.dao.impl.RoleDaoImpl;

public class PermissionTestPermission {
	
	
	@Test
	public void testAddPermission(){
		/*
		 * ����Dao��
		 * ���ھ�
		 * RoleDao
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("Ȩ�޹���");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
		
	}
	@Test
	public void testAddViewPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("5fa99265d0a4495dbeac13bd1a0efe8d");
		permission.setName("�鿴Ȩ��");
		permission.setUrl("viewPermission.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("5fa99265d0a4495dbeac13bd1a0efe8d");
		permission.setName("���Ȩ��");
		permission.setUrl("addPermission.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void testGetRole(){
		IRoleDao roleDao=new RoleDaoImpl();
		Roles role=roleDao.getById("246342109fa44d5e805fcd90fb0a93fc");
		System.out.println("��ɫ����"+role.getRoleName());
	}
	
}