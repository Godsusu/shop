package cn.edu.guet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.lanqiao.util.PageModel;
import com.alibaba.fastjson.JSON;
import cn.edu.guet.bean.Permission;
import cn.edu.guet.service.IPermissionService;
import cn.edu.guet.service.impl.PermissionServiceImpl;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class PermissionController extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String viewPermission(HttpServletRequest request, HttpServletResponse response) {
		String currentPage = request.getParameter("currentPage");
		System.out.println("当前页：" + currentPage);
		if (currentPage == null) {
			currentPage = "1";
		}
		IPermissionService permissionService = new PermissionServiceImpl();
		PageModel<Permission> pm = permissionService.getAll(Integer.parseInt(currentPage));
		request.setAttribute("pm", pm);
		List<Permission> permissions = permissionService.getAllPermission();
		request.setAttribute("permissions", permissions);
		request.setAttribute("currentPage", currentPage);

		return "permission/viewPermission.jsp";
	}

	public void getAllPermission(HttpServletRequest request, HttpServletResponse response) {
		try {
			/**
			 * 获取所有权限
			 */
			IPermissionService permissionService = new PermissionServiceImpl();
			List<Permission> allPermissions = permissionService.getAllPermission();

			/**
			 * 获取某个角色已有的权限 要先拿到该角色的roleId
			 * 接下来要和Service层交互：那么到底是PermissionService还是RoleService
			 */
			String roleId = request.getParameter("roleId");
			List<Permission> ownedPermissions = permissionService.getPermissionByRoleId(roleId);

			for (Permission allPermission : allPermissions) {
				for (Permission ownedPermission : ownedPermissions) {
					if (ownedPermission.getPermissionid().equals(allPermission.getPermissionid())) {
						allPermission.setChecked("true");
					}
				}
				if (allPermission.getIsParent().equals("true")) {
					allPermission.setOpen("true");
				}
			}

			response.setContentType("application/json;charset=GBK");
			PrintWriter out = response.getWriter();
			out.write(JSON.toJSONString(allPermissions));// 把新增已有的权限的checked属性的所有的权限转成JSON并发送给浏览器
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void savePermission(HttpServletRequest request, HttpServletResponse response) {
		try {
			Map<String, String[]> map = request.getParameterMap();

			Permission p = new Permission();
			p.setPermissionid(UUID.randomUUID().toString().replace("-", ""));// 手动封装：因为界面上没有传递参数过来
			BeanUtils.populate(p, map);

			IPermissionService permissionService = new PermissionServiceImpl();
			permissionService.savePermission(p);

			response.setContentType("text/plain;charset=GBK");
			PrintWriter out = response.getWriter();
			out.write("保存成功");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}
}
