package cn.edu.guet.customer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.util.SendEmail;
import org.lanqiao.util.TransactionHandle;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class CustomerController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	int emailCode;
	int createverifycode;
	String phone;
	
	public String regist(HttpServletRequest request, HttpServletResponse response){
		return "customer/register.html";
	}
	
	public String customerLogin(HttpServletRequest request, HttpServletResponse response){
		return "customer/customerLogin.html";
	}
	
	public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response){  //向手机发送验证码
		phone=request.getParameter("phone");
		createverifycode=123456;//(int)((Math.random()*9+1)*100000);
		System.out.println(createverifycode);
		System.out.println(phone);
		try {
			//SendDemo.send(phone, createverifycode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void comparisonVerifyCode(HttpServletRequest request, HttpServletResponse response){  //对比手机验证码是否正确
		try {
			int verifycode=Integer.parseInt(request.getParameter("verifycode"));
			response.setContentType("text/plain;charset=GBK");
			PrintWriter out=response.getWriter();
			if(createverifycode==verifycode){
				out.write("验证码正确");
			}else{
				out.write("验证码错误");
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	public void sendEmailCode(HttpServletRequest request, HttpServletResponse response){  //发送邮箱验证码
			emailCode=123456;//(int)((Math.random()*9+1)*100000);
			String recipient=request.getParameter("email");
			SendEmail.send(recipient,emailCode);
	}
	
	public void registerEmailCode(HttpServletRequest request, HttpServletResponse response){  //判断邮箱验证码是否正确以及注册
		try {
			Customer customer=new Customer();
			response.setContentType("text/plain;charset=GBK");
			PrintWriter out=response.getWriter();
			int mailCode=Integer.parseInt(request.getParameter("mailCode"));
			String customerId=UUID.randomUUID().toString().replace("-","");
			String username=request.getParameter("code");
			String password=request.getParameter("password");
			String mailBox=request.getParameter("mail");
			System.out.println(customerId+":"+username+":"+password+":"+mailBox+":"+phone);
			if(mailCode==emailCode){
				customer.setCustomerId(customerId);
				customer.setUsername(username);
				customer.setPassword(password);
				customer.setMailBox(mailBox);
				customer.setPhone(phone);
				
				ICustomerService customerService=(ICustomerService) new TransactionHandle().createProxyObject((ICustomerService) new TransactionHandle().createProxyObject(new CustomerServiceImpl()));		
				customerService.savaCustomer(customer);
				out.write("验证成功");			
			}
			else{
				out.write("验证码错误");
			}
			out.flush();
			out.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			/**
			 * 把错误消息返回界面
			 */
			e.printStackTrace();
		}
	}
	public void selectCustomer(HttpServletRequest request, HttpServletResponse response){  //判断邮箱验证码是否正确以及注册
		String id="0dd142a4992147a99dd93013908cefb5";
		try {
			ICustomerService customerService=(ICustomerService) BeanFactory.getInstance().getBean("customerService");
			Customer customer=customerService.selectCustomer(id);
			
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void checkCustomer(HttpServletRequest request, HttpServletResponse response){
		String user=request.getParameter("user");	
		String password=request.getParameter("password");
		response.setContentType("text/plain;charset=gbk");
		PrintWriter out=null;
		ICustomerService customerService=(ICustomerService) BeanFactory.getInstance().getBean("customerService");
		try {
			
			Customer customer=customerService.checkCustomer(user);
			if(customer!=null){
				if(password.equals(customer.getPassword())){
					HttpSession session=request.getSession();
					session.setAttribute("customerName", customer.getUsername());
					session.setAttribute("customerid", customer.getCustomerId());
					out=response.getWriter();
					out.write("true");
				}else{
					out=response.getWriter();
					out.write("密码错误");
				}
				
			}else{
				out=response.getWriter();
				out.write("账号不存在");
			}
			out.flush();
			out.close();
		} catch (DaoException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public void getUserName(HttpServletRequest request, HttpServletResponse response){
		try {
			PrintWriter out=null;
			response.setContentType("application/json;charset=gbk");
			out=response.getWriter();
			HttpSession session=request.getSession();
			out.write(JSON.toJSONString(session.getAttribute("customerName")));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
}
