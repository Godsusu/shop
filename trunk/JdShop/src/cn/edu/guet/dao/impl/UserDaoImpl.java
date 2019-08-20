package cn.edu.guet.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.dbutils.QueryRunner;
import org.lanqiao.util.DBConnection;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.bean.Users;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.IUserDao;

public class UserDaoImpl extends BaseDaoImpl<Users> implements IUserDao {

	IRoleDao roleDao=null;
	public UserDaoImpl(){
		roleDao=new RoleDaoImpl();
	}
	@Override
	public Users login(String username, String password) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="SELECT * FROM users WHERE username=? AND password=?";
		conn=DBConnection.getConn();
		Users user=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,username);
			pstmt.setString(2,password);
			rs=pstmt.executeQuery();
			if(rs.next()){
				user=new Users();
				user.setUsersId(rs.getString("USERSID"));
				user.setUsername(username);
				String roleId=rs.getString("ROLESID");//�õ�roleId
				Roles role=roleDao.getById(roleId);
				user.setRole(role);//�û��ͽ�ɫ����
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.closeConn(conn);
		return user;
	}
	@Override
	public Set<Permission> getPermission(String username){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="SELECT rolesid FROM users WHERE username=?";
		conn=DBConnection.getConn();
		Set<Permission> permissions=new TreeSet<Permission>();
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,username);
			rs=pstmt.executeQuery();
			String roleId=null;
			if(rs.next()){
				roleId=rs.getString("ROLESID");//�õ�roleId
			}
			sql="SELECT p.* FROM permission p,rolespermission rp WHERE rp.permissionid=p.permissionid AND rp.rolesid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, roleId);//������
			rs=pstmt.executeQuery();
			while(rs.next()){
				/**
				 * ÿѭ��һ�ξ���һ��Ȩ�ޣ�����Ҫnewһ��Ȩ�޶��󣬲���һһ���Ȩ�޵����Խ��и�ֵ����
				 */
				Permission permission=new Permission();
				permission.setPermissionid(rs.getString("PERMISSIONID"));
				permission.setPid(rs.getString("PID"));
				permission.setName(rs.getString("NAME"));
				permission.setUrl(rs.getString("URL"));
				permission.setIcon(rs.getString("ICON"));
				permission.setIsParent(rs.getString("ISPARENT"));
				permission.setTarget(rs.getString("TARGET"));
				permission.setIconSkin(rs.getString("ICONSKIN"));
				permissions.add(permission);
			}
			return permissions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.closeConn(conn);
		return null;
	}
	@Override
	public void saveGrant(String userId, String roleId) {
		QueryRunner qr=null;
		Connection conn=DBConnection.getConn();
		try {
			qr=new QueryRunner();//�������ظ���JDBC����
			String sql="UPDATE users SET rolesid=? WHERE usersid=?";
			qr.update(conn, sql, roleId,userId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DBConnection.closeConn(conn);
	}
}