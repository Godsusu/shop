package cn.edu.guet.customer;

import java.sql.SQLException;

public class CustomerServiceImpl implements ICustomerService {
	ICustomerDao customerDao;
	public CustomerServiceImpl(){
		customerDao = new CustomerDaoImpl();
	}
	
	public void savaCustomer(Customer customer) {
		try {
			customerDao.save(customer);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
