package cn.edu.guet.customer;

import cn.edu.guet.exception.DaoException;

public interface ICustomerService{
	public void savaCustomer(Customer customer) throws DaoException;
	public Customer selectCustomer(String id) throws DaoException;
	public Customer checkCustomer(String user) throws DaoException;
}

