package cn.edu.guet.service.impl;

import cn.edu.guet.bean.Customer;
import cn.edu.guet.dao.ICustomer;
import cn.edu.guet.dao.impl.CustomerDaoImpl;
import cn.edu.guet.service.ICustomerServise;

public class CustomerServiseImpl implements ICustomerServise {

	ICustomer customerDao;
	public CustomerServiseImpl(){
		customerDao=new CustomerDaoImpl();
	}
	@Override
	public void Save(Customer customer) {
		customerDao.save(customer);
	}

}
