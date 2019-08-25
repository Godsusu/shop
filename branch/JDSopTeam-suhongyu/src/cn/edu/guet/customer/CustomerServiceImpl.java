package cn.edu.guet.customer;

import java.sql.SQLException;

import org.lanqiao.util.Dic;

import cn.edu.guet.exception.DaoException;

public class CustomerServiceImpl implements ICustomerService {
	ICustomerDao customerDao;
	public CustomerServiceImpl(){
		customerDao = new CustomerDaoImpl();
	}
	
	public void savaCustomer(Customer customer) throws DaoException {
		try {
			customerDao.save(customer);
		} catch (SQLException e) {
			throw new DaoException(Dic.SAVE_FAILED);
		}
	}
	
}
