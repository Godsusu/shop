package cn.edu.guet.orderproduct;

import java.sql.SQLException;

public class OrderProductServiceImpl implements IOrderProductService {
	IOrderProductDao orderProductDao=new OrderProductDaoImpl();

	@Override
	public void save(OrderProduct orderProduct) {
		try {
			orderProductDao.save(orderProduct);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
