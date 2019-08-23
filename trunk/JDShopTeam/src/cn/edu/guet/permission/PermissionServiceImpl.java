package cn.edu.guet.permission;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.PageModel;

public class PermissionServiceImpl implements IPermissionService {
	IPermissionDao permissionDao;
	public PermissionServiceImpl(){
		permissionDao=new PermissionDaoImpl();
	}
	
	public List<Permission> getAllPermission() {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			List<Permission> list=permissionDao.selectAll();
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}
	public List<Permission> getPermissionById(String roleId) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			List<Permission> list=permissionDao.getPermissionById(roleId);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}
	
	
	public PageModel<Permission> getAll(int currentPage) {
		try {
			Connection conn=null;
			conn=DBConnection.getConn();
			PageModel<Permission> pm= permissionDao.selectAll(currentPage);
			List<Permission> Permissions=pm.getList();
			for(Permission Permission:Permissions){
				if(Permission.getIconSkin()==null){
					Permission.setIconSkin("");
				}
				if(Permission.getUrl()==null){
					Permission.setUrl("");
				}
			}
			return pm;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}

	
	
	@Override
	public void savePermission(Permission p) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			permissionDao.save(p);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		
	}

	@Override
	public void deletePermission(String permissionId) {
		Connection conn=DBConnection.getConn();
		try {
			conn.setAutoCommit(false);
			permissionDao.deletePermissionChild(permissionId);
			
			List<Permission> list=permissionDao.getChildsById(permissionId);
			for(Permission p:list){
				permissionDao.deletePermissionChild(p.getPermissionId());
				permissionDao.delete(p.getPermissionId());
			}
			
			permissionDao.delete(permissionId);
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
	public void updatePermission(Permission p) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			permissionDao.update(p);
			conn.commit();
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}		
	}
}
