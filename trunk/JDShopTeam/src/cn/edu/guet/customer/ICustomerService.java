package cn.edu.guet.customer;

import cn.edu.guet.exception.DaoException;

public interface ICustomerService{
	public void savaCustomer(Customer customer) throws DaoException;
}
