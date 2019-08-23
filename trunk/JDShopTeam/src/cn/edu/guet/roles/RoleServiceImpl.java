package cn.edu.guet.roles;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;


public class RoleServiceImpl implements IRoleService {
	IRoleDao roleDao;
	public RoleServiceImpl(){
		roleDao=new RoleDaoImpl();
	}
	public List<Roles> getAllRole() {
		try {
			List<Roles> list=roleDao.selectAll();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}
	@Override
	public void saveGrant(String roleId, String[] permissionIds) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);		
			roleDao.saveGrant(roleId, permissionIds);
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
	
}
