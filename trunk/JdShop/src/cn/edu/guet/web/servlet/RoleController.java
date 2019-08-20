package cn.edu.guet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import cn.edu.guet.bean.Roles;
import cn.edu.guet.service.IRoleService;
import cn.edu.guet.service.impl.RoleServiceImpl;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class RoleController extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public void saveGrant(HttpServletRequest request, HttpServletResponse response) {
		try {
			String roleId = request.getParameter("roleId");
			String permissionIds[] = request.getParameterValues("permissionIds[]");
			IRoleService roleService = new RoleServiceImpl();
			roleService.saveGrant(roleId, permissionIds);

			response.setContentType("text/plain;charset=GBK");
			PrintWriter out = response.getWriter();
			out.write("��Ȩ�ɹ��������µ�¼");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String viewRole(HttpServletRequest request, HttpServletResponse response) {

		// return֮ǰҪ��ȥ���ݿ�ѡ����н�ɫ����ѯ���������ҷ���ĳ��������request��
		IRoleService roleService = new RoleServiceImpl();
		List<Roles> roles = roleService.getAll();
		request.setAttribute("roles", roles);

		return "role/viewRole.jsp";
	}

	public String grantRole(HttpServletRequest request, HttpServletResponse response) {

		try {

			String roleName = request.getParameter("roleName");
			String roleId = request.getParameter("roleId");
			request.setAttribute("roleId", roleId);
			roleName = new String(roleName.getBytes("ISO-8859-1"), "GBK");
			request.setAttribute("roleName", roleName);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return "role/grantUser.jsp";
	}
}
