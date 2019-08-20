package cn.edu.guet.service.impl;

import java.util.List;
import java.util.Set;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.bean.Users;
import cn.edu.guet.dao.IRoleDao;
import cn.edu.guet.dao.IUserDao;
import cn.edu.guet.dao.impl.RoleDaoImpl;
import cn.edu.guet.dao.impl.UserDaoImpl;
import cn.edu.guet.service.IUserService;

public class UserServiceImpl implements IUserService {

	IUserDao userDao=null;
	IRoleDao roleDao=null;
	public UserServiceImpl(){
		//���ģʽ֮���󹤳�ģʽ
		userDao=new UserDaoImpl();
		roleDao=new RoleDaoImpl();
	}
	@Override
	public void saveUser(Users user) {
		userDao.save(user);//���õ�BaseDao��save����

	}

	@Override
	public Users login(String username, String password) {//�о�Service�㣨ҵ���߼��㣩��ȫû�б�Ҫ����
		return userDao.login(username, password);
	}
	@Override
	public Set<Permission> getPermission(String username) {
		return userDao.getPermission(username);
	}
	@Override
	public PageModel<Users> getAllUser(int currentPage) {
		//PageModel��������ʵ��Users������
		PageModel<Users> pm=userDao.selectAll(currentPage);
		List<Users> users=pm.getList();
		for(Users user:users){
			Roles role=roleDao.getById(user.getRolesId());
			user.setRole(role);//�û�������ɫ
		}
		return pm;
	}
	@Override
	public void saveGrant(String userId, String roleId) {
		userDao.saveGrant(userId, roleId);
	}
	@Override
	public int deleteUser(String userId) {
		return userDao.delete(userId);
	}
}