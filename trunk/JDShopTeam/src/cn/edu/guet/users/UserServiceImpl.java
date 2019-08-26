package cn.edu.guet.users;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.Dic;
import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.permission.Permission;
import cn.edu.guet.roles.IRoleDao;
import cn.edu.guet.roles.RoleDaoImpl;
import cn.edu.guet.roles.Roles;

public class UserServiceImpl implements IUserService {
	IUserDao userDao=null;
	IRoleDao roleDao=null;
	
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	@Override
	public void saveUser(Users user) throws DaoException {
		try {
			userDao.save(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.SAVE_FAILED);
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
	
	public void grantUser(String userId,String roleId) throws DaoException{
		try {
			userDao.grantUser(userId, roleId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException("ÊÚÈ¨Ê§°Ü");
		}
	}
	
	@Override
	public void deleteUser(String userId) throws DaoException {
		try {
			userDao.delete(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.DELETE_FAILED);
		}
	}

}
