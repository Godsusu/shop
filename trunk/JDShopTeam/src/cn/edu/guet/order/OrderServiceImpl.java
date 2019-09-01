package cn.edu.guet.order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.UUIDString;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.orderproduct.IOrderProductService;
import cn.edu.guet.orderproduct.OrderProduct;
import cn.edu.guet.orderproduct.OrderProductServiceImpl;
import cn.edu.guet.shoppingcart.IShoppingCartService;
import cn.edu.guet.shoppingcart.ShoppingCart;

public class OrderServiceImpl implements IOrderService {

	IOrderDao orderDao=new OrderDaoImpl();
	IShoppingCartService shoppingcartService=(IShoppingCartService) BeanFactory.getInstance().getBean("shoppingCartService");
	IOrderProductService orderProductService=new OrderProductServiceImpl();
	@Override
	public void saveOrder(Orders order,String customerId) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			orderDao.save(order);
			List<ShoppingCart> list = new ArrayList<ShoppingCart>();
			list=shoppingcartService.getAllOrder(customerId);
			
			Iterator<ShoppingCart> iter=list.iterator();

			System.out.println(JSON.toJSONString(list));
			while(iter.hasNext()){
				ShoppingCart shopping=new ShoppingCart();
				shopping=iter.next();
				OrderProduct orderProduct=new OrderProduct();
				orderProduct.setOrderProductId(UUIDString.getId());
				orderProduct.setOrderId(order.getOrderId());
				orderProduct.setProductId(shopping.getProductid());
				orderProduct.setNum(Integer.parseInt(shopping.getProductnum()));
				orderProductService.save(orderProduct);
				System.out.println(shopping.getShoppingcartid());
				shoppingcartService.delete(shopping.getShoppingcartid());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		
	}

}
