package cn.edu.guet.shoppingcart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.product.Product;

public class ShoppingCartServiceImpl implements IShoppingCartService {

	IShoppingCartDao iscd=new ShoppingCartDaoImpl();
	
	
	@Override
	public void addProductToCart(ShoppingCart s) throws DaoException {
		try {
			iscd.save(s);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public List<ShoppingCart> selectShoppingCart(String customerId) {
		System.out.println(iscd);
		List<ShoppingCart> list=iscd.selectAllProduct(customerId);
		return list;
	}
	@Override
	public void updataShoppingCart(ShoppingCart shoppingCart) {
		try {
			iscd.update(shoppingCart);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public List<ShoppingCart> getAllOrder(String customerId) {
		List<ShoppingCart> list=new ArrayList<ShoppingCart>();
		list=iscd.getAllOrder(customerId);
		return list;
	}
	@Override
	public void delete(String shoppingcartId) {
		try {
			iscd.delete(shoppingcartId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
