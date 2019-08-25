package org.lanqiao.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {	
	public static ThreadLocal<Connection> map=new ThreadLocal<Connection>();
	public static Connection getConn() {
		Connection conn=map.get();
		
		if(conn!=null){
			return conn;
		}
		else{
			Properties prop=new Properties();
		    InputStream in;
			try {
				in = Class.forName("org.lanqiao.util.DBConnection").getResourceAsStream("/db.properties");
				prop.load(in);
				String url=prop.getProperty("url");
				Class.forName(prop.getProperty("driver"));
				conn=DriverManager.getConnection(url,prop.getProperty("user"),prop.getProperty("password"));
				map.set(conn);
				return conn;
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void closeConn() {
		try {
			Connection conn=map.get();
			if(conn!=null){
				map.remove();
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
