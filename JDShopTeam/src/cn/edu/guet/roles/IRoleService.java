package cn.edu.guet.roles;

import java.util.List;


public interface IRoleService {
	List<Roles> getAllRole();
	void saveGrant(String roleId,String permissionIds[]);
}
