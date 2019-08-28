package cn.edu.guet.web.servlet.base;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName=request.getParameter("method");
		try {
			if (methodName != null) {
				Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);// 可变长参数：可以等同看作数组
				String url = (String) method.invoke(this, request, response);
				if (url != null) {
					if(url.startsWith("redirect")){
						url=url.substring(url.indexOf("/")+1);//把redirect:/去除
						response.sendRedirect(url);
					}
					else{
						request.getRequestDispatcher(url).forward(request, response);
					}
				}
			}
			else{
				request.getRequestDispatcher("500Error.jsp").forward(request, response);
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
