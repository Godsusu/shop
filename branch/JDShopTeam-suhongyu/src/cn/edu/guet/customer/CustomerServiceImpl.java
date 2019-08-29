package cn.edu.guet.customer;

import java.sql.SQLException;

import org.lanqiao.util.Dic;

import cn.edu.guet.exception.DaoException;

public class CustomerServiceImpl implements ICustomerService {
	ICustomerDao customerDao;
	
	
	
	public void setCustomerDao(ICustomerDao customerDao) {
		this.customerDao = customerDao;
	}


	public void savaCustomer(Customer customer) throws DaoException {
		try {
			customerDao.save(customer);
		} catch (SQLException e) {
			throw new DaoException(Dic.SAVE_FAILED);
		}
	}
	public Customer selectCustomer(String id) throws DaoException{
		Customer customer=null;
		try {
			customer=customerDao.getById(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
}
