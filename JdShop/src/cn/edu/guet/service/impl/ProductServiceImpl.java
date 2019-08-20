package cn.edu.guet.service.impl;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Product;
import cn.edu.guet.dao.IProductDao;
import cn.edu.guet.dao.impl.ProductDaoImpl;
import cn.edu.guet.service.IProductService;

public class ProductServiceImpl implements IProductService {

	IProductDao productDao;
	public ProductServiceImpl(){
		productDao=new ProductDaoImpl();
	}
	@Override
	public PageModel<Product> getAllProduct(int currentPage) {
		// TODO Auto-generated method stub
		return productDao.selectAll(currentPage);
	}

}
