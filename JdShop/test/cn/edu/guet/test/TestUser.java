package cn.edu.guet.test;

import java.util.UUID;
import org.junit.Test;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.bean.Users;
import cn.edu.guet.dao.IBaseDao;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.IUserDao;
import cn.edu.guet.dao.impl.BaseDaoImpl;
import cn.edu.guet.dao.impl.PermissionDaoImpl;
import cn.edu.guet.dao.impl.RoleDaoImpl;
import cn.edu.guet.dao.impl.UserDaoImpl;
import junit.framework.TestCase;

public class TestUser extends TestCase{
	
	@Test
	public void testAddUser(){
		/*
		 * 调用Dao层
		 * 高内聚
		 * RoleDao
		 */
		Users user=new Users();
		user.setUsersId((UUID.randomUUID().toString().replace("-","")));
		user.setUsername("zhangsan");
		user.setPassword("zs1234");
		user.setRolesId("246342109fa44d5e805fcd90fb0a93fc");
		
		IUserDao userDao=new UserDaoImpl();
		userDao.save(user);
	}
	@Test
	public void testAddUser01(){
		/*
		 * 调用Dao层
		 * 高内聚
		 * RoleDao
		 */
		Users user=new Users();
		user.setUsersId((UUID.randomUUID().toString().replace("-","")));
		user.setUsername("wangwu");
		user.setPassword("ww1234");
		user.setRolesId("768f7da64444452b9e64785c83224371");
		
		IUserDao userDao=new UserDaoImpl();
		userDao.save(user);
	}
	@Test
	public void testLogin(){
		IUserDao userDao=new UserDaoImpl();
		Users user=userDao.login("wangwu", "ww1234");//用户----角色----权限
		
		this.assertEquals(3, user.getRole().getPermissions().size());//第一个参数：期待的值；第二个参数：actual实际的值
	}
}