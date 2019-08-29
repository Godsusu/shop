package cn.edu.guet.product;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.Dic;
import org.lanqiao.util.PageModel;

import cn.edu.guet.exception.DaoException;

public class ProductServiceImpl implements IProductService {
	
	private IProductDao productDao;
	
	
	public void setProductDao(IProductDao productDao) {
		this.productDao = productDao;
	}

	public PageModel<Product> getAllProduct(int currentPage) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			PageModel<Product> pm= productDao.selectAll(currentPage);
			List<Product> Products=pm.getList();
			for(Product Product:Products){
				if(Product.getPicurl()==null){
					Product.setPicurl("");
				}
				if(Product.getDescInfo()==null){
					Product.setDescInfo("");
				}
			}
			return pm;
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			DBConnection.closeConn();
		}
		return null;
	}

	@Override
	public void deleteProduct(String productId) throws DaoException {
		try {
			productDao.delete(productId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.DELETE_FAILED);
		}
	}

	@Override
	public void saveProduct(Product p) throws DaoException {
		try {
			productDao.save(p);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.SAVE_FAILED);
		}
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		try {
			productDao.update(product);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.UPDATE_FAILED);
		}
	}

	@Override
	public Product getOneProduct(String productId) throws DaoException {
		Product product=new Product();
		try {
			product=productDao.getById(productId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DaoException(Dic.QUERY_FAILED);
		}
		return product;
	}

	@Override
	public List<Product> selectAllProduct(String categoryName) throws DaoException {
		List<Product> list=productDao.selectAllProduct(categoryName);
		return list;
	}
	public String selectCategoryID(String categoryName) throws DaoException {
		String categoryid=productDao.selectCategoryID(categoryName);
		return categoryid;
	}
}
