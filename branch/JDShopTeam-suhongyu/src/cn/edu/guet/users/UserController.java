package cn.edu.guet.users;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.util.PageModel;
import org.lanqiao.util.TransactionHandle;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.permission.Permission;
import cn.edu.guet.roles.IRoleService;
import cn.edu.guet.roles.Roles;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class UserController extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String deleteUser(HttpServletRequest request, HttpServletResponse response)
	{
		String userId=request.getParameter("userId");
		IUserService userService=(IUserService) new TransactionHandle().createProxyObject(BeanFactory.getInstance().getBean("userService"));
		try {
			userService.deleteUser(userId);
		} catch (DaoException e) {
			/**
			 * 返回错误信息
			 */
			e.printStackTrace();
		}
		/*
		response.addCookie(new Cookie("rs",));
		
		Cookie[] cookies = request.getCookies();*/
		return "user?method=viewUser";
	}
	
	public String saveGrant(HttpServletRequest request, HttpServletResponse response)
	{
		String roleId=request.getParameter("roleId");
		String userId=request.getParameter("userId");
		IUserService userService=(IUserService) new TransactionHandle().createProxyObject(BeanFactory.getInstance().getBean("userService"));
		try {
			userService.grantUser(userId,roleId);
		} catch (DaoException e) {
			/**
			 * 返回错误信息
			 */
			e.printStackTrace();
		}
		
		return "user?method=viewUser";
	}
	public String grantUser(HttpServletRequest request, HttpServletResponse response){
		String userId=request.getParameter("userId");
		String username=request.getParameter("username");
		request.setAttribute("username", username);
		request.setAttribute("userId", userId);
		IRoleService roleService=(IRoleService) BeanFactory.getInstance().getBean("roleService");
		List<Roles> roles=roleService.getAllRole();
		request.setAttribute("roles", roles);
		return "user/grantUser.jsp";
	}
	
	public String viewUser(HttpServletRequest request, HttpServletResponse response){
		return "user/viewUser.html";			
	}
	
	public void viewUserList(HttpServletRequest request, HttpServletResponse response){
		try {
			String currentPage=request.getParameter("currentPage");
			System.out.println(currentPage);
			if(currentPage==null){
				currentPage="1";
			}
			IUserService userService=(IUserService) BeanFactory.getInstance().getBean("userService");
			PageModel<Users> pm=userService.getAllUsers(Integer.parseInt(currentPage));
			pm.setCurrentPage(Integer.parseInt(currentPage));
			response.setContentType("application/json;charset=GBK");
			PrintWriter out=response.getWriter();
			System.out.print(JSON.toJSONString(pm));
			out.write(JSON.toJSONString(pm));
			out.flush();
			out.close();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}		
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response){		
			String username=request.getParameter("username");
			String password=request.getParameter("password");			
			IUserService userService=(IUserService) BeanFactory.getInstance().getBean("userService");
			Users user=userService.login(username, password);
			HttpSession session=request.getSession();
			session.setAttribute("user",user);			
			return "index.jsp";	
	}
	
	public void getMenu(HttpServletRequest request, HttpServletResponse response){
		try {
			String username=request.getParameter("username");
			IUserService userService=(IUserService) BeanFactory.getInstance().getBean("userService");
			Set<Permission> permission=userService.getPermission(username);	
			for(Permission per:permission){
				if(per.getIsParent().equals("true")){
					per.setOpen("true");
				}
			}
			response.setContentType("application/json;charset=GBK");
			System.out.println("json: "+JSON.toJSONString(permission));
			PrintWriter out=response.getWriter();
			out.print(JSON.toJSONString(permission));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String addUser(HttpServletRequest request, HttpServletResponse response){	
		return "user/addUser.html";	
	}
	
	public String saveUser(HttpServletRequest request, HttpServletResponse response){
		Users user=new Users();
		user.setUsersId(UUID.randomUUID().toString().replace("-",""));
		user.setUsername(request.getParameter("username"));
		IUserService userService=(IUserService) new TransactionHandle().createProxyObject(BeanFactory.getInstance().getBean("userService"));
		try {
			userService.saveUser(user);
		} catch (DaoException e) {
			/**
			 * 返回错误信息
			 */
			e.printStackTrace();
		}
		return "user?method=viewUser";
	}
	
	public String logout(HttpServletRequest request, HttpServletResponse response){
		request.getSession().invalidate();
		return "login.html";
	}
}
