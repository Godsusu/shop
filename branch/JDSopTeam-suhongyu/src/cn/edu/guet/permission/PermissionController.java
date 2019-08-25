package cn.edu.guet.permission;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.lanqiao.util.PageModel;
import org.lanqiao.util.TransactionHandle;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class PermissionServlet
 */
public class PermissionController extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void getAllPermission(HttpServletRequest request, HttpServletResponse response){
		try {
			IPermissionService permissionService=(IPermissionService) BeanFactory.getInstance().getBean("permissionService");
			List<Permission> allPermissions=permissionService.getAllPermission();		
			String roleId=request.getParameter("roleId");
			List<Permission> ownedPermissions=permissionService.getPermissionById(roleId);
			for(Permission allPermission:allPermissions){
				for(Permission ownedPermission:ownedPermissions){
					if(ownedPermission.getPermissionId().equals(allPermission.getPermissionId())){
						allPermission.setChecked("true");
					}		
				}
				if(allPermission.getIsParent().equals("true")){
					allPermission.setOpen("true");
				}
			}

			response.setContentType("application/json;charset=GBK");
			PrintWriter out=response.getWriter();
			out.write(JSON.toJSONString(allPermissions));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String viewPermission(HttpServletRequest request, HttpServletResponse response){	
		return "permission/viewPermission.html";
	}
	
	public void getPermision(HttpServletRequest request, HttpServletResponse response){
		try {
			String currentPage = request.getParameter("currentPage");
			System.out.println("当前页：" + currentPage);
			if (currentPage == null) {
				currentPage = "1";
			}
			IPermissionService permissionService=(IPermissionService) BeanFactory.getInstance().getBean("permissionService");
			PageModel<Permission> pm=permissionService.getAll(Integer.parseInt(currentPage));
			pm.setCurrentPage(Integer.parseInt(currentPage));
			System.out.println(JSON.toJSONString(pm));
			response.setContentType("application/json;charset=GBK");
			PrintWriter out=response.getWriter();
			out.write(JSON.toJSONString(pm));
			out.flush();
			out.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("null")
	public void savePermission(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		try {
			//Map<String,String[]> map=request.getParameterMap();
			
			String pname=request.getParameter("name");
			String pSkin=request.getParameter("iconSkin");
			String isParent=request.getParameter("isParent");
			String url=request.getParameter("url");
			String pid=request.getParameter("pid");
			
			Permission permission=new Permission();
			permission.setPermissionId(UUID.randomUUID().toString().replace("-",""));
			//BeanUtils.populate(permission, map);
			
			//System.out.println(pname);
			permission.setName(pname);
			permission.setIconSkin(pSkin);
			permission.setIsParent(isParent);
			permission.setUrl(url);
			permission.setPid(pid);
			
			IPermissionService permissionService=(IPermissionService) new TransactionHandle().createProxyObject((IPermissionService) BeanFactory.getInstance().getBean("permissionService"));
			permissionService.savePermission(permission);
			
			response.setContentType("text/plain;charset=GBK");
			out=response.getWriter();
			out.write("保存成功");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			out.write(e.getMessage());
		}
		out.flush();
		out.close();
	}
	
	public String addPermission(HttpServletRequest request, HttpServletResponse response){
		IPermissionService permissionService=(IPermissionService) BeanFactory.getInstance().getBean("permissionService");
		List<Permission> pm=permissionService.getAllPermission();
		request.setAttribute("permissions", pm);

		return "permission/addPermission.jsp";
	}
	
	public void updatePermission(HttpServletRequest request, HttpServletResponse response){
		String permissionid=request.getParameter("permissionId");
		String pname=request.getParameter("name");
		String pSkin=request.getParameter("iconSkin");
		String url=request.getParameter("url");
		
		Permission permission=new Permission();
		permission.setPermissionId(permissionid);
		permission.setName(pname);
		permission.setIconSkin(pSkin);
		permission.setUrl(url);
		
		IPermissionService permissionService=(IPermissionService) new TransactionHandle().createProxyObject((IPermissionService) BeanFactory.getInstance().getBean("permissionService"));
		try {
			permissionService.updatePermission(permission);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		response.setContentType("text/plain;charset=GBK");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.write("保存成功");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deletePermission(HttpServletRequest request, HttpServletResponse response){
		PrintWriter out=null;
		response.setContentType("text/plain;charset=GBK");
		String permissionId=request.getParameter("permissionId");
		IPermissionService permissionService=(IPermissionService) new TransactionHandle().createProxyObject((IPermissionService) BeanFactory.getInstance().getBean("permissionService"));
		try {
			out=response.getWriter();
			permissionService.deletePermission(permissionId);
			out.write("删除成功");		
		} catch (Exception e) {
			e.printStackTrace();
			out.write(e.getMessage());
		}
		out.flush();
		out.close();
	}
}
