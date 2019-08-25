package cn.edu.guet.roles;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.Dic;

import cn.edu.guet.exception.DaoException;


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
	public void saveGrant(String roleId, String[] permissionIds) throws DaoException {	
		try {
			roleDao.saveGrant(roleId, permissionIds);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.SAVE_FAILED);
		}
	}
	
}
