package cn.edu.guet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.util.PageModel;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.bean.Users;
import cn.edu.guet.service.IRoleService;
import cn.edu.guet.service.IUserService;
import cn.edu.guet.service.impl.RoleServiceImpl;
import cn.edu.guet.service.impl.UserServiceImpl;
import cn.edu.guet.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserController extends BaseServlet {
	private static final long serialVersionUID = 1L;

	IUserService userService = new UserServiceImpl();
	
	
	public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
		String userId=request.getParameter("userId");
		if(userService.deleteUser(userId)==1){
			return "user?method=viewUser";
		}else{
			request.setAttribute("errorMsg", "ɾ��ʧ�ܣ�����ϵά����Ա");
			return "error.jsp";
		}
	}
	public String  saveGrant(HttpServletRequest request, HttpServletResponse response) {
		String roleId=request.getParameter("roleId");
		String userId=request.getParameter("userId");
		IUserService userService = new UserServiceImpl();
		userService.saveGrant(userId, roleId);
		return "user?method=viewUser";
	}
	public String grantUser(HttpServletRequest request, HttpServletResponse response) {
		String username=request.getParameter("username");
		String userId=request.getParameter("userId");
		//i.����Ȩ�ޣ������н�ɫ��ѯ������������ʾ����Ȩҳ���У�
		IRoleService roleService=new RoleServiceImpl();
		List<Roles> roles=roleService.getAll();
		request.setAttribute("roles", roles);
		request.setAttribute("username", username);
		request.setAttribute("userId", userId);
		return "user/userGrant.jsp";
		//�õ����н�ɫ��Ҫ�ѽ�ɫ�ļ��Ϸ���request������
		
		//ii.�ѱ���Ȩ���û���ҲҪ��ʾ��ҳ����
		
		
	}
	public String viewUser(HttpServletRequest request, HttpServletResponse response) {

		String currentPage = request.getParameter("currentPage");
		System.out.println("��ǰҳ��" + currentPage);
		if (currentPage == null) {
			currentPage = "1";
		}
		IUserService userService = new UserServiceImpl();
		PageModel<Users> pm = userService.getAllUser(Integer.parseInt(currentPage));

		request.setAttribute("pm", pm);// pmЯ�����������ݣ�1����ʵ�����ݣ�2����ҳ��
		request.setAttribute("currentPage", currentPage);

		return "user/viewUser.jsp";

	}

	public String saveUser(HttpServletRequest request, HttpServletResponse response) {
		Users user = new Users();
		user.setUsersId(UUID.randomUUID().toString().replace("-", ""));
		user.setUsername(request.getParameter("username"));
		IUserService userService = new UserServiceImpl();
		userService.saveUser(user);

		// �����û���Ϻ�ҳ��Ҫ��ת�����鿴�û���
		return "user?method=viewUser";

	}

	public String addUser(HttpServletRequest request, HttpServletResponse response) {
		return "user/addUser.html";
	}

	public String login(HttpServletRequest request, HttpServletResponse response) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		IUserService userService = new UserServiceImpl();
		Users user = userService.login(username, password);

		// ����session��
		HttpSession session = request.getSession();
		session.setAttribute("user", user);

		return "index.jsp";
	}

	public void getMenu(HttpServletRequest request, HttpServletResponse response) {
		try {
			String username = request.getParameter("username");

			IUserService userService = new UserServiceImpl();
			Set<Permission> permission = userService.getPermission(username);
			for(Permission per:permission){
				if(per.getIsParent().equals("true")){
					per.setOpen("true");
				}
			}

			response.setContentType("application/json;charset=GBK");
			System.out.println("json: " + JSON.toJSONString(permission));
			PrintWriter out = response.getWriter();
			out.print(JSON.toJSONString(permission));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String logout(HttpServletRequest request, HttpServletResponse response){
		//��λ��session����
		request.getSession().invalidate();//��sessionʧЧ
		return "login.html";
	}
}