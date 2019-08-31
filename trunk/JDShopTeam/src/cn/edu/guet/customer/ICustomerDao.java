package cn.edu.guet.customer;

import cn.edu.guet.base.IBaseDao;
import cn.edu.guet.exception.DaoException;

public interface ICustomerDao extends IBaseDao<Customer>{
	public Customer checkCustomer(String user) throws DaoException;
}

