package cn.edu.guet.order;

import java.sql.SQLException;

public class OrderServiceImpl implements IOrderService {

	IOrderDao orderDao=new OrderDaoImpl();
	@Override
	public void saveOrder(Orders order) {
		try {
			orderDao.save(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
