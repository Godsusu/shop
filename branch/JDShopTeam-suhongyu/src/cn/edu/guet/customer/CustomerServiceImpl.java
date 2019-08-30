package cn.edu.guet.customer;

import java.sql.SQLException;

import org.lanqiao.util.Dic;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;

public class CustomerServiceImpl implements ICustomerService {
	ICustomerDao customerDao=(ICustomerDao) BeanFactory.getInstance().getBean("customerDao");
	
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


	@Override
	public Customer checkCustomer(String user) throws DaoException {
		Customer c=customerDao.checkCustomer(user);	
		return c;
	}
}
