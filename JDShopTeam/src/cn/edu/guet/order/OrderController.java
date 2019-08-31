package cn.edu.guet.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.util.ProductModel;
import org.lanqiao.util.UUIDString;


import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.orderproduct.IOrderProductService;
import cn.edu.guet.orderproduct.OrderProduct;
import cn.edu.guet.orderproduct.OrderProductServiceImpl;
import cn.edu.guet.shoppingcart.IShoppingCartService;
import cn.edu.guet.shoppingcart.ShoppingCart;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class OrderController extends BaseServlet {
	
	public void submitOrder(HttpServletRequest request, HttpServletResponse response){
		try {
			String orderId=UUID.randomUUID().toString().replace("-","");
			String address=request.getParameter("address");
			String ammount=request.getParameter("ammount");
			String cashInfo="待支付";
			HttpSession session=request.getSession();
			String customerId=(String) session.getAttribute("customerid");
			String status="已下单";
			Orders order=new Orders();
			order.setOrderId(orderId);
			order.setCustomerId(customerId);
			order.setAmmount(Float.valueOf(ammount));
			System.out.println(Float.valueOf(ammount));
			
			order.setStatus(status);
			order.setBuyerInfo(address);
			/*Date day=new Date();
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=sdf.format(day);*/
			System.out.println(new java.sql.Date(System.currentTimeMillis()));
			order.setOrderDate(new java.sql.Date(System.currentTimeMillis()));
			order.setCashInfo(cashInfo);
			IOrderService orderService=new OrderServiceImpl();
			orderService.saveOrder(order);
			
			
			IShoppingCartService shoppingcartService=(IShoppingCartService) BeanFactory.getInstance().getBean("shoppingCartService");
			List<ShoppingCart> list = shoppingcartService.getAllOrder(customerId);
			
			Iterator<ShoppingCart> iter=list.iterator();
			IOrderProductService orderProductService=new OrderProductServiceImpl();
			while(iter.hasNext()){
				ShoppingCart shopping=new ShoppingCart();
				OrderProduct orderProduct=new OrderProduct();
				orderProduct.setOrderProductId(UUIDString.getId());
				orderProduct.setOrderId(orderId);
				orderProduct.setProductId(shopping.getProductid());
				//orderProductService.save(orderProduct);
			}
			PrintWriter out=response.getWriter();
			out.write("1");
			out.flush();
			out.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
