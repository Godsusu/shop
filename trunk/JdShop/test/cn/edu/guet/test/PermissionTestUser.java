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
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid(null);
		permission.setName("商城后台管理");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void testAddUserPermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("用户管理");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void testAddViewUserPermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("9a1ec10b285646f580d47780b4e5896c");
		permission.setName("查看用户");
		permission.setUrl("viewUser.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addUserPermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("9a1ec10b285646f580d47780b4e5896c");
		permission.setName("添加用户");
		permission.setUrl("addUser.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
}