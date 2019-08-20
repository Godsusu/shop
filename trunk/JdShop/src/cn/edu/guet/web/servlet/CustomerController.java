package cn.edu.guet.web.servlet;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.lanqiao.util.SendDemo;

import cn.edu.guet.bean.Customer;
import cn.edu.guet.service.ICustomerServise;
import cn.edu.guet.service.impl.CustomerServiseImpl;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class CustomerController extends BaseServlet {
	private static final long serialVersionUID = 1L;
       

	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response){
		try {
			String phone=request.getParameter("phone");
			String code=(String) UUID.randomUUID().toString().replace("-", "").toUpperCase().subSequence(0, 5);
			code="12345";
			request.getSession().setAttribute("code" , code);
			//SendDemo.sendPhone(phone, code);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkCode(HttpServletRequest request, HttpServletResponse response){
		try {
			String code=request.getParameter("code");
			if(request.getSession().getAttribute("code").equals(code)){
				response.setContentType("text/plain");
				PrintWriter out1=response.getWriter();
				out1.write("OK");
				out1.flush();
				out1.close();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void sendMileCode(HttpServletRequest request, HttpServletResponse response){
		try {
			String mail=request.getParameter("mailBox");
			System.out.println(mail);
			String mailCode=(String) UUID.randomUUID().toString().replace("-", "").toUpperCase().subSequence(0, 5);
			request.getSession().setAttribute("mailCode" , mailCode);
			SendDemo.sendMile(mail, mailCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkMileCode(HttpServletRequest request, HttpServletResponse response){
		try {
			String mailCode=request.getParameter("mailCode");
			if(request.getSession().getAttribute("mailCode").equals(mailCode)){
				response.setContentType("text/plain");
				PrintWriter out=response.getWriter();
				out.write("OK");
				out.flush();
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void save(HttpServletRequest request, HttpServletResponse response){
		try {
			Customer customer=new Customer();
			customer.setCustomerId(UUID.randomUUID().toString().replace("-", ""));
			BeanUtils.populate(customer, request.getParameterMap());
			
			ICustomerServise customerServise=new CustomerServiseImpl();
			customerServise.Save(customer);
			
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}