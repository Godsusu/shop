package cn.edu.guet.permission;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.Dic;
import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;

public class PermissionServiceImpl implements IPermissionService {
	IPermissionDao permissionDao;
	public PermissionServiceImpl(){
		permissionDao=(IPermissionDao) BeanFactory.getInstance().getBean("permissionDao");
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
	public void savePermission(Permission p) throws DaoException {		
		try{
			permissionDao.save(p);
		}catch(SQLException e){
			throw new DaoException(Dic.SAVE_FAILED);
		}
	}

	@Override
	public void deletePermission(String permissionId) throws DaoException{		
		try {
			permissionDao.deletePermissionChild(permissionId);			
			List<Permission> list=permissionDao.getChildsById(permissionId);
			for(Permission p:list){
				permissionDao.deletePermissionChild(p.getPermissionId());
				permissionDao.delete(p.getPermissionId());
			}			
			permissionDao.delete(permissionId);
		} catch (SQLException e) {
			throw new DaoException("É¾³ýÊ§°Ü");
		}	
	}

	@Override
	public void updatePermission(Permission p) throws SQLException {
		permissionDao.update(p);
	}
}
