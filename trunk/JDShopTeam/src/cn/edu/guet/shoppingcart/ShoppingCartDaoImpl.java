package cn.edu.guet.shoppingcart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.lanqiao.util.DBConnection;

import cn.edu.guet.base.BaseDaoImpl;

public class ShoppingCartDaoImpl extends BaseDaoImpl<ShoppingCart> implements IShoppingCartDao {

	@Override
	public List<ShoppingCart> selectAllProduct(String customerid) {
		Connection conn=null;
		
		PreparedStatement pstmt=null;
		conn=DBConnection.getConn();
		ResultSet rs=null;
		List<ShoppingCart> list=new ArrayList<ShoppingCart>();
		try {			
			String sql="SELECT * FROM shoppingcart WHERE customerid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,customerid);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ShoppingCart shoppingCart=new ShoppingCart();
				shoppingCart.setShoppingcartid(rs.getString("SHOPPINGCARTID"));
				shoppingCart.setCustomerid(rs.getString("CUSTOMERID"));
				shoppingCart.setProductid(rs.getString("PRODUCTID"));
				shoppingCart.setProductnum(rs.getString("PRODUCTNUM"));
				list.add(shoppingCart);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}

	@Override
	public List<ShoppingCart> getAllOrder(String customerId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		conn=DBConnection.getConn();
		ResultSet rs=null;
		List<ShoppingCart> list=new ArrayList<ShoppingCart>();
		try {			
			String sql="SELECT * FROM shoppingcart WHERE ischoose='true' AND customerid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,customerId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				ShoppingCart shoppingCart=new ShoppingCart();
				shoppingCart.setShoppingcartid(rs.getString("SHOPPINGCARTID"));
				shoppingCart.setCustomerid(rs.getString("CUSTOMERID"));
				shoppingCart.setProductid(rs.getString("PRODUCTID"));
				shoppingCart.setProductnum(rs.getString("PRODUCTNUM"));
				list.add(shoppingCart);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}
}
