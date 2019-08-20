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
			request.setAttribute("errorMsg", "删除失败，请联系维护人员");
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
		//i.所有权限（把所有角色查询出来，并且显示在授权页面中）
		IRoleService roleService=new RoleServiceImpl();
		List<Roles> roles=roleService.getAll();
		request.setAttribute("roles", roles);
		request.setAttribute("username", username);
		request.setAttribute("userId", userId);
		return "user/userGrant.jsp";
		//拿到所有角色后，要把角色的集合放入request作用域
		
		//ii.把被授权的用户名也要显示在页面上
		
		
	}
	public String viewUser(HttpServletRequest request, HttpServletResponse response) {

		String currentPage = request.getParameter("currentPage");
		System.out.println("当前页：" + currentPage);
		if (currentPage == null) {
			currentPage = "1";
		}
		IUserService userService = new UserServiceImpl();
		PageModel<Users> pm = userService.getAllUser(Integer.parseInt(currentPage));

		request.setAttribute("pm", pm);// pm携带了两个数据：1、真实的数据；2、总页数
		request.setAttribute("currentPage", currentPage);

		return "user/viewUser.jsp";

	}

	public String saveUser(HttpServletRequest request, HttpServletResponse response) {
		Users user = new Users();
		user.setUsersId(UUID.randomUUID().toString().replace("-", ""));
		user.setUsername(request.getParameter("username"));
		IUserService userService = new UserServiceImpl();
		userService.saveUser(user);

		// 保存用户完毕后，页面要跳转到“查看用户”
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

		// 放入session中
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
		//如何获得session对象？
		request.getSession().invalidate();//让session失效
		return "login.html";
	}
}