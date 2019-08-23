package cn.edu.guet.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.PageModel;

import cn.edu.guet.permission.Permission;
import cn.edu.guet.roles.IRoleDao;
import cn.edu.guet.roles.RoleDaoImpl;
import cn.edu.guet.roles.Roles;



public class UserServiceImpl implements IUserService {
	IUserDao userDao=null;
	IRoleDao roleDao=null;
	public UserServiceImpl(){
		userDao=new UserDaoImpl();
		roleDao=new RoleDaoImpl();
	}
	@Override
	public void saveUser(Users user) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			userDao.save(user);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
		finally{
			DBConnection.closeConn();
		}
	}

	@Override
	public Users login(String username, String password) {
		Users user=null;
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			user = userDao.login(username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		
		return user;
	}
	
	@Override
	public Set<Permission> getPermission(String username) {
		Connection conn=null;
		
		try {
			conn=DBConnection.getConn();
			Set<Permission> set=userDao.getPermission(username);
			DBConnection.closeConn();
			return set;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}
	
	
	@Override
	public PageModel<Users> getAllUsers(int currentPage) {
		try {
			PageModel<Users> pm=userDao.selectAll(currentPage);
			List<Users> users=pm.getList();
			for(Users user:users){
				Roles role = roleDao.getById(user.getRolesId());
				if(role==null){
					role = new Roles();
					role.setRoleName("");
				}
				user.setRole(role);
			}
			return pm;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}
	public void grantUser(String userId,String roleId){
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			userDao.grantUser(userId, roleId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnection.closeConn();
		}
	}
	@Override
	public void deleteUser(String userId) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			userDao.delete(userId);
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnection.closeConn();
		}	
	}

}
