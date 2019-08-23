package cn.edu.guet.users;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.lanqiao.util.DBConnection;

import cn.edu.guet.base.BaseDaoImpl;
import cn.edu.guet.permission.Permission;
import cn.edu.guet.roles.IRoleDao;
import cn.edu.guet.roles.RoleDaoImpl;
import cn.edu.guet.roles.Roles;

public class UserDaoImpl extends BaseDaoImpl<Users> implements IUserDao {
	IRoleDao roleDao=null;
	public UserDaoImpl(){
		roleDao=new RoleDaoImpl();
	}
	
	public Users login(String username, String password) throws SQLException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="SELECT * FROM users WHERE username=? AND password=?";
		conn=DBConnection.getConn();
		Users user=null;
		Set<Permission> permissions=new HashSet<Permission>();
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,username);
		pstmt.setString(2,password);
		rs=pstmt.executeQuery();
		if(rs.next()){
			user=new Users();
			user.setUsersId(rs.getString("USERSID"));
			user.setUsername(username);
			String roleId=rs.getString("ROLESID");
			user.setRolesId(roleId);
			Roles role=roleDao.getById(roleId);
			user.setRole(role);
			
		}
		sql="SELECT p.* FROM permission p,rolespermission rp WHERE p.permissionid=rp.permissionid AND rp.rolesid=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,user.getRolesId());
		rs=pstmt.executeQuery();
		while(rs.next()){
			Permission permission=new Permission();
			permission.setPermissionId(rs.getString("PERMISSIONID"));
			permission.setPid(rs.getString("pid"));
			permission.setName(rs.getString("name"));
			permission.setUrl(rs.getString("url"));
			permission.setIsParent(rs.getString("isParent"));
			permission.setIcon(rs.getString("icon"));
			permission.setTarget(rs.getString("target"));
			permission.setIconSkin(rs.getString("iconskin"));
			permissions.add(permission);
		}
		user.getRole().setPermissions(permissions);
		
		return user;
	}
	@Override
	public Set<Permission> getPermission(String username) throws SQLException{
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="SELECT rolesid FROM users WHERE username=?";
		conn=DBConnection.getConn();
		System.out.println("UserDaoImpl:"+conn);
		Set<Permission> permissions=new TreeSet<Permission>();
		
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1,username);
		rs=pstmt.executeQuery();
		String roleId=null;
		if(rs.next()){
			roleId=rs.getString("ROLESID");//拿到roleId
		}
		sql="SELECT p.* FROM permission p,rolespermission rp WHERE rp.permissionid=p.permissionid AND rp.rolesid=?";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, roleId);//方法链
		rs=pstmt.executeQuery();
		while(rs.next()){
			/**
			 * 每循环一次就是一个权限，就需要new一个权限对象，并且一一针对权限的属性进行赋值操作
			 */
			Permission permission=new Permission();
			permission.setPermissionId(rs.getString("PERMISSIONID"));
			permission.setPid(rs.getString("PID"));
			permission.setName(rs.getString("NAME"));
			permission.setUrl(rs.getString("URL"));
			permission.setIcon(rs.getString("ICON"));
			permission.setIsParent(rs.getString("ISPARENT"));
			permission.setTarget(rs.getString("target"));
			permission.setIconSkin(rs.getString("iconskin"));
			permissions.add(permission);
		}
		return permissions;
		
	}

	@Override
	public void grantUser(String userId, String roleId) throws SQLException{
		Users user=new Users();
		user.setUsersId(userId);
		user.setRolesId(roleId);
		this.update(user);
	}
}