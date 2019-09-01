package cn.edu.guet.orderproduct;

import java.sql.Connection;
import java.sql.SQLException;

import org.lanqiao.util.DBConnection;

public class OrderProductServiceImpl implements IOrderProductService {
	IOrderProductDao orderProductDao=new OrderProductDaoImpl();
	Connection conn=null;
	@Override
	public void save(OrderProduct orderProduct) {
		try {
			conn=DBConnection.getConn();
			orderProductDao.save(orderProduct);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		
	}
	
	
}
