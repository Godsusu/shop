package cn.edu.guet.product;

import java.util.List;

import cn.edu.guet.base.IBaseDao;

public interface IProductDao extends IBaseDao<Product> {
	List<Product> selectAllProduct(String categoryId);
	String selectCategoryID(String categoryName);
}
