package cn.edu.guet.shoppingcart;

import java.sql.SQLException;
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
		List<ShoppingCart> list=iscd.selectAllProduct(customerId);
		return list;
	}

}
