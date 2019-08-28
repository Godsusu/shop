package cn.edu.guet.permission;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lanqiao.util.DBConnection;

import cn.edu.guet.base.BaseDaoImpl;


public class PermissionDaoImpl extends BaseDaoImpl<Permission> implements IPermissionDao {
	public List<Permission> getPermissionById(String roleId) throws SQLException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="SELECT p.* FROM permission p,rolespermission rp WHERE rp.permissionid=p.permissionid AND rp.rolesid=?";
		conn=DBConnection.getConn();
		List<Permission> permissions=new ArrayList<Permission>();
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, roleId);//·½·¨Á´
		rs=pstmt.executeQuery();
		while(rs.next()){
			Permission permission=new Permission();
			permission.setPermissionId(rs.getString("PERMISSIONID"));
			permission.setPid(rs.getString("PID"));
			permission.setName(rs.getString("NAME"));
			permission.setUrl(rs.getString("URL"));
			permission.setIcon(rs.getString("ICON"));
			permission.setIsParent(rs.getString("ISPARENT"));
			permission.setTarget(rs.getString("target"));
			permissions.add(permission);
		}
		return permissions;
		
		
	}

	@Override
	public void deletePermissionChild(String id) throws SQLException {
		String sql="DELETE FROM rolespermission WHERE permissionid=?";
		QueryRunner qr=new QueryRunner();
		Connection conn=DBConnection.getConn();
		qr.update(conn, sql, id);
	}

	@Override
	public List<Permission> getChildsById(String permissionId) throws SQLException{
		
		String sql="SELECT c.* FROM permission p,permission c WHERE c.pid=p.permissionid AND p.permissionid=?";
		QueryRunner qr=new QueryRunner();
		Connection conn=DBConnection.getConn();
		List<Permission> list=qr.query(conn, sql,new BeanListHandler<>(Permission.class), permissionId);
		return list;
		
	
	}
}
