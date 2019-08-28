package cn.edu.guet.product;


import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;

public interface IProductService {
	PageModel<Product> getAllProduct(int currentPage);
	void deleteProduct(String productId) throws DaoException;
	void saveProduct(Product p) throws DaoException;
	void updateProduct(Product product) throws DaoException;
}
