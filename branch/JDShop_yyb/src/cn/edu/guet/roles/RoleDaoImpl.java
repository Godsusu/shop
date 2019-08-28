package cn.edu.guet.roles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.lanqiao.util.DBConnection;
import cn.edu.guet.base.BaseDaoImpl;

public class RoleDaoImpl extends BaseDaoImpl<Roles> implements IRoleDao {

	@Override
	public void saveGrant(String roleId, String[] permissionIds) throws SQLException{
		
			Connection conn=null;
			PreparedStatement pstmt=null;
			String sql="DELETE FROM rolespermission WHERE rolesid=?";
			conn=DBConnection.getConn();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,roleId);
			pstmt.executeUpdate();
			
			sql="INSERT INTO rolespermission VALUES(?,?)";
			pstmt=conn.prepareStatement(sql);
			for(int i=0;i<permissionIds.length;i++){
				pstmt.setString(1,roleId);
				pstmt.setString(2,permissionIds[i]);
				pstmt.executeUpdate();
			}
		
	}

}
