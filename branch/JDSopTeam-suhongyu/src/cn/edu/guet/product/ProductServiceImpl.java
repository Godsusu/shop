package cn.edu.guet.product;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.PageModel;

public class ProductServiceImpl implements IProductService {
	
	private IProductDao productDao;
	public ProductServiceImpl(){
		productDao=new ProductDaoImpl();
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
	public void deleteProduct(String productId) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			productDao.delete(productId);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally{
			DBConnection.closeConn();
		}
	}

	@Override
	public void saveProduct(Product p) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			productDao.save(p);
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
	}

	@Override
	public void updateProduct(Product product) {
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			productDao.update(product);
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
	}

}
