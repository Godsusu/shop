package cn.edu.guet.customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.lanqiao.util.DBConnection;

import cn.edu.guet.base.BaseDaoImpl;
import cn.edu.guet.exception.DaoException;

public class CustomerDaoImpl extends BaseDaoImpl<Customer> implements ICustomerDao {

	@SuppressWarnings("null")
	@Override
	public Customer checkCustomer(String user) throws DaoException {
		Connection conn=null;
		PreparedStatement pstmt=null;
		conn=DBConnection.getConn();
		ResultSet rs=null;
		Customer customer=new Customer();
		try {			
			String sql="SELECT * FROM customer WHERE USERNAME=?";
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1,user);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				customer.setCustomerId(rs.getString("CUSTOMERID"));
				customer.setUsername(rs.getString("USERNAME"));
				customer.setPassword(rs.getString("PASSWORD"));
				customer.setMailBox(rs.getString("MAILBOX"));
				customer.setPhone(rs.getString("PHONE"));
			}
			return customer;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}

}
