package cn.edu.guet.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.lanqiao.util.DBConnection;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.base.BaseDaoImpl;

public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao {

	@Override
	public List<Product> selectAllProduct(String categoryId) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		conn=DBConnection.getConn();
		ResultSet rs=null;
		List<Product> list=new ArrayList<Product>();
		try {			
			String sql="SELECT * FROM product WHERE categoryid=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,categoryId);
			rs=pstmt.executeQuery();
			while(rs.next()){
				Product product=new Product();
				product.setProductId(rs.getString("productid"));
				product.setName(rs.getString("name"));
				product.setPicurl(rs.getString("picurl"));
				product.setPrice(rs.getFloat("price"));
				product.setOnlineDate(rs.getDate("onlineDate"));
				product.setDescInfo(rs.getString("descInfo"));
				list.add(product);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}

	@Override
	public String selectCategoryID(String categoryName) {
		Connection conn=null;
		PreparedStatement pstmt=null;
		conn=DBConnection.getConn();
		ResultSet rs=null;
		String categoryId="";
		try {			
			String sql="SELECT categoryId FROM category WHERE name=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,categoryName);
			rs=pstmt.executeQuery();
			while(rs.next()){
				categoryId=rs.getString("categoryid");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return categoryId;
	}
	
	
	
	
}
