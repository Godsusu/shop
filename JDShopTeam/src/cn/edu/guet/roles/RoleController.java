package cn.edu.guet.roles;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.util.TransactionHandle;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.web.servlet.base.BaseServlet;


public class RoleController extends BaseServlet {
	private static final long serialVersionUID = 1L;


	public String viewRole(HttpServletRequest request, HttpServletResponse response){
		IRoleService roleService=(IRoleService) BeanFactory.getInstance().getBean("roleService");
		List<Roles> roles=roleService.getAllRole();
		request.setAttribute("roles", roles);
		return "role/viewRole.jsp";
	}
	
	public String grantRole(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		String rolesId=request.getParameter("rolesId");
		String rolesName=request.getParameter("rolesName");
		rolesName=new String(rolesName.getBytes("ISO-8859-1"),"GBK");
		System.out.print(rolesName);
		request.setAttribute("rolesId",rolesId);
		request.setAttribute("roleName",rolesName);
		return "role/grantRole.jsp";
	}
	
	public void saveGrant(HttpServletRequest request, HttpServletResponse response){
		try {
			String roleId=request.getParameter("roleId");
			String permissionIds[]=request.getParameterValues("permissionIds[]");
			IRoleService roleService=(IRoleService) new TransactionHandle().createProxyObject((IRoleService) BeanFactory.getInstance().getBean("roleService"));
			roleService.saveGrant(roleId, permissionIds);
			System.out.println(permissionIds[0]);
			response.setContentType("text/plain;charset=GBK");
			PrintWriter out=response.getWriter();
			out.write("授权成功,请重新登录");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			/**
			 * 返回信息
			 */
			e.printStackTrace();
		}
	}
	
	public void addRole(HttpServletRequest request, HttpServletResponse response){
		System.out.println("111111111");
	}
	
}
