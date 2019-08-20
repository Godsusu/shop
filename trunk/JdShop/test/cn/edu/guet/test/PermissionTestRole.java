package cn.edu.guet.test;

import java.util.UUID;

import org.junit.Test;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.impl.PermissionDaoImpl;
import cn.edu.guet.dao.impl.RoleDaoImpl;

public class PermissionTestRole {
	
	@Test
	public void testAddRootPermission(){
		/*
		 * ����Dao��
		 * ���ھ�
		 * RoleDao
		 */
		Roles role=new Roles();
		role.setRolesId((UUID.randomUUID().toString().replace("-","")));
		role.setRoleName("����Ա");
		
		IRoleDao roleDao=new RoleDaoImpl();
		roleDao.save(role);
		
	}
	@Test
	public void testAddRolePermission(){
		/*
		 * ����Dao��
		 * ���ھ�
		 * RoleDao
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("��ɫ����");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
		
	}
	@Test
	public void testAddViewRolePermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("bd6c942889b14db692d4574b616ea22a");
		permission.setName("�鿴��ɫ");
		permission.setUrl("viewRole.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addRolePermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("bd6c942889b14db692d4574b616ea22a");
		permission.setName("��ӽ�ɫ");
		permission.setUrl("addRole.do");
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