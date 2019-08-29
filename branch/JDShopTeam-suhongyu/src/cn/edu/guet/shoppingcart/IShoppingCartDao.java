package cn.edu.guet.shoppingcart;

import java.util.List;

import cn.edu.guet.base.IBaseDao;

public interface IShoppingCartDao extends IBaseDao<ShoppingCart> {
	List<ShoppingCart> selectAllProduct(String customerid);
	
}
