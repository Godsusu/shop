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
		 * 调用Dao层
		 * 高内聚
		 * RoleDao
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("e655e297c3a34569b46b455f3885477f");
		permission.setName("权限管理");
		permission.setUrl(null);
		permission.setIcon(null);
		permission.setIsParent("true");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
		
	}
	@Test
	public void testAddViewPermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("5fa99265d0a4495dbeac13bd1a0efe8d");
		permission.setName("查看权限");
		permission.setUrl("viewPermission.do");
		permission.setIcon(null);
		permission.setIsParent("false");
		
		IPermissionDao permissionDao=new PermissionDaoImpl();
		permissionDao.save(permission);
	}
	@Test
	public void addPermission(){
		/*
		 * 调用Dao层
		 * 构造Permission对象，然后调用IPermissionDao的save保存
		 */
		Permission permission=new Permission();
		permission.setPermissionid((UUID.randomUUID().toString().replace("-","")));
		permission.setPid("5fa99265d0a4495dbeac13bd1a0efe8d");
		permission.setName("添加权限");
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
		System.out.println("角色名："+role.getRoleName());
	}
	
}