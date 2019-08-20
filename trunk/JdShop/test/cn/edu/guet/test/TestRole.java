package cn.edu.guet.test;

import java.util.UUID;
import org.junit.Test;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.dao.IBaseDao;
import cn.edu.guet.dao.IPermissionDao;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.impl.BaseDaoImpl;
import cn.edu.guet.dao.impl.PermissionDaoImpl;
import cn.edu.guet.dao.impl.RoleDaoImpl;

public class TestRole {
	
	@Test
	public void testAdmin(){
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
	public void testNormal(){
		/*
		 * ����Dao��
		 * ���ھ�
		 * RoleDao
		 */
		Roles role=new Roles();
		role.setRolesId((UUID.randomUUID().toString().replace("-","")));
		role.setRoleName("��ͨ�û�");
		
		IRoleDao roleDao=new RoleDaoImpl();
		roleDao.save(role);
		
	}
	@Test
	public void testGetRole(){
		IRoleDao roleDao=new RoleDaoImpl();
		Roles role=roleDao.getById("246342109fa44d5e805fcd90fb0a93fc");
		System.out.println("��ɫ����"+role.getRoleName());
	}
	
}