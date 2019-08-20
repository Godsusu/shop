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
		 * 调用Dao层
		 * 高内聚
		 * RoleDao
		 */
		Roles role=new Roles();
		role.setRolesId((UUID.randomUUID().toString().replace("-","")));
		role.setRoleName("管理员");
		
		IRoleDao roleDao=new RoleDaoImpl();
		roleDao.save(role);
		
	}
	@Test
	public void testAddRolePermission(){
		/*
		 * 调用Dao层
		 * 高内聚
		 * RoleDao
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("角色管理");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
		
	}
	@Test
	public void testAddViewRolePermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("bd6c942889b14db692d4574b616ea22a");
		permission.setName("查看角色");
		permission.setUrl("viewRole.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addRolePermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("bd6c942889b14db692d4574b616ea22a");
		permission.setName("添加角色");
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
		System.out.println("角色名："+role.getRoleName());
	}
	
}