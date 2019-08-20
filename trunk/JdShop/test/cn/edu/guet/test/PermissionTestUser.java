package cn.edu.guet.test;

import java.util.UUID;

import org.junit.Test;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.impl.PermissionDaoImpl;

public class PermissionTestUser {
	
	@Test
	public void addRootPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid(null);
		permission.setName("�̳Ǻ�̨����");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void testAddUserPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("�û�����");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void testAddViewUserPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("9a1ec10b285646f580d47780b4e5896c");
		permission.setName("�鿴�û�");
		permission.setUrl("viewUser.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addUserPermission(){
		/*
		 * ����Dao��
		 * ����Permission����Ȼ�����IPermissionDao��save����
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("9a1ec10b285646f580d47780b4e5896c");
		permission.setName("����û�");
		permission.setUrl("addUser.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
}