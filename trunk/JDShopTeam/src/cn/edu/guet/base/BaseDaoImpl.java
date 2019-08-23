package cn.edu.guet.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.PageModel;


public class BaseDaoImpl<T> implements IBaseDao<T> {
	PreparedStatement pstmt;
	Connection conn; 
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		persistentClass = (Class<T>) type.getActualTypeArguments()[0];////////////////////////////////////////
	}

	@Override
	public void save(T t) throws SQLException{
		conn = DBConnection.getConn();
		String sql = "INSERT INTO " + t.getClass().getSimpleName().toLowerCase() + " (";
		List<Method> list = this.matchPojoMethods(t, "get");
		Iterator<Method> iter = list.iterator();
		Object obj[] = new Object[list.size()];
		int i = 0;
		// 拼接字段顺序 insert into table name(id,name,email,
		while (iter.hasNext()) {
			Method method = iter.next();
			try {
				if(method.invoke(t, new Object[] {})!=null){
					sql += method.getName().substring(3).toLowerCase() + ",";
					if (method.getReturnType().getSimpleName().indexOf("Date") != -1) {
						try {	
							//obj[i] = sbf.format(method.invoke(t, new Object[] {}));
							obj[i] =(Date)method.invoke(t, new Object[] {});
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} else {
						try {
							if(method.invoke(t, new Object[] {})!=null){ //把实体对象中null的属性排除在外
								obj[i] = method.invoke(t, new Object[] {});
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					}
					i++;
				}
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}

		// 去掉最后一个,符号insert insert into table name(id,name,email) values(
		sql = sql.substring(0, sql.lastIndexOf(",")) + ") values(";

		// 拼装预编译SQL语句insert insert into table name(id,name,email) values(?,?,?,
		for (int j = 0; j < i; j++) {
			sql += "?,";
		}

		// 去掉SQL语句最后一个,符号insert insert into table name(id,name,email)
		// values(?,?,?);
		sql = sql.substring(0, sql.lastIndexOf(",")) + ")";

		// 到此SQL语句拼接完成,打印SQL语句
		System.out.println("自动生成的SQL语句：" + sql);

		
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(sql);
		for (int j=0; j<i; j++) {
			pstmt.setObject(j+1,obj[j]);
		}
		pstmt.executeUpdate();
		conn.commit();
		pstmt.close();
		
	}

	/**/
	private List<Method> matchPojoMethods(T entity, String methodName) {
		// 获得当前Pojo所有方法对象
		Method[] methods = entity.getClass().getDeclaredMethods();

		List<Method> list = new ArrayList<Method>();
		for (int index = 0; index < methods.length; index++) {
			if (methods[index].getName().indexOf(methodName) != -1) {
				list.add(methods[index]);
			}
		}
		return list;
	}

	
	@Override
	public void update(T t) throws SQLException{
		conn = DBConnection.getConn();
		String sql = "UPDATE " + t.getClass().getSimpleName().toLowerCase() + " SET ";
		String className=persistentClass.getSimpleName().toLowerCase();
		List<Method> list = this.matchPojoMethods(t, "get");
		Iterator<Method> iter = list.iterator();
		List<Object> valueList=new ArrayList<Object>();
		
		while (iter.hasNext()) {
			Method method = iter.next();
			try {
				Object obj=method.invoke(t);
				if(obj!=null && !method.getName().substring(3).toLowerCase().equals(className+"id") && method.invoke(t, new Object[] {})!=null){
					valueList.add(obj);
					sql += method.getName().substring(3).toLowerCase() + "=?,";
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		iter = list.iterator();
		while(iter.hasNext())
		{
			Method method = iter.next();
			try {
				if(method.getName().substring(3).toLowerCase().equals(className+"id")){
					Object obj=method.invoke(t);
					valueList.add(obj);
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		sql=sql.substring(0,sql.lastIndexOf(","));
		
		sql+=" WHERE "+className+"id=?";
		System.out.println("SQL组装："+sql);
		
		conn.setAutoCommit(false);
		pstmt = conn.prepareStatement(sql);
		for (int j = 0; j < valueList.size(); j++) {
			System.out.println(j+1+"   "+valueList.get(j));
			pstmt.setObject(j + 1,valueList.get(j));
		}
		pstmt.executeUpdate();
		conn.commit();
		pstmt.close();
		
	}
	
	public <O> int delete(O id) throws SQLException {
		conn = DBConnection.getConn();
		String className=persistentClass.getSimpleName().toLowerCase();
		String sql="DELETE FROM "+className+" WHERE "+className+"id=?";
		System.out.println("自动生成sql语句："+sql);
		pstmt=conn.prepareStatement(sql);
		pstmt.setObject(1, id);
		pstmt.executeQuery();
		return 1;
	}
	
	public <O> T getById(O id) throws SQLException{
		conn = DBConnection.getConn();
		String className=persistentClass.getSimpleName().toLowerCase();
		String sql="SELECT * FROM "+className+" WHERE "+className+"id=?";
		conn=DBConnection.getConn();
		T t=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setObject(1, id);
			ResultSet rs=pstmt.executeQuery();
			if(rs.next()) {
				t=persistentClass.newInstance();
				List<Method> list=this.matchPojoMethods(t, "set");
				Iterator<Method> iter=list.iterator();
				while(iter.hasNext()) {
					Method method=iter.next();
					String type=method.getParameterTypes()[0].getName();
					if(type.indexOf("String")!=-1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					}
					else if(type.indexOf("Int")!=-1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					}
				}
			}
			pstmt.close();
			return t;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
		
	}
	 
	public PageModel<T> selectAll(int currentPage) throws SQLException{
		int rowsPerPage=10;		
		int endRow=currentPage*rowsPerPage;
		int startRow=(currentPage-1)*rowsPerPage+1;
		int totalRows=getTotalRows();
		int totalPage=0;
		if(totalRows%rowsPerPage==0){
			totalPage=totalRows/rowsPerPage;
		}
		else{
			totalPage=totalRows/rowsPerPage+1;
		}
		
		String tableName=persistentClass.getSimpleName().toLowerCase();
		String sql="SELECT * FROM (SELECT rownum rn,t.* FROM (SELECT * FROM "+tableName+") t WHERE rownum<=?) WHERE rn>=?";
		conn=DBConnection.getConn();
		List<T> objList=new ArrayList<T>();
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,endRow);
			pstmt.setInt(2,startRow);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				T t=persistentClass.newInstance();
				List<Method> list=this.matchPojoMethods(t, "set");
				Iterator<Method> iter=list.iterator();
				while(iter.hasNext()) {
					Method method=iter.next();
					String type=method.getParameterTypes()[0].getName();
					if(type.indexOf("String")!=-1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					}
					else if(type.indexOf("Int")!=-1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					}
					else if(type.indexOf("Float")!=-1) {
						method.invoke(t, rs.getFloat(method.getName().substring(3).toLowerCase()));
					}
					else if(type.indexOf("Date")!=-1) {
						method.invoke(t,rs.getDate(method.getName().substring(3).toLowerCase()));
					}
				}
				objList.add(t);
			}
			PageModel<T> pm=new PageModel<T>();
			pm.setList(objList);
			pm.setTotalPage(totalPage);	
			return pm;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int getTotalRows() throws SQLException{
		conn = DBConnection.getConn();
		
		String tableName=persistentClass.getSimpleName().toLowerCase();
		String sql="SELECT COUNT(*) FROM "+tableName;
		conn=DBConnection.getConn();
		pstmt=conn.prepareStatement(sql);
		ResultSet rs=pstmt.executeQuery();
		rs.next();
		int totalRows=rs.getInt(1);//获取第一列
		return totalRows;
	
	}
	
	public List<T> selectAll() throws SQLException{ //未分页的
		conn = DBConnection.getConn();
		String sql="SELECT * FROM "+persistentClass.getSimpleName().toLowerCase();
		conn=DBConnection.getConn();
		List<T> objList=new ArrayList<T>();
		try {
			System.out.println("自动生成SQL:"+sql);
			pstmt=conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			while(rs.next()) {
				T t=persistentClass.newInstance();
				List<Method> list=this.matchPojoMethods(t, "set");
				Iterator<Method> iter=list.iterator();
				while(iter.hasNext()) {
					Method method=iter.next();
					String type=method.getParameterTypes()[0].getName();
					if(type.indexOf("String")!=-1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					}
					else if(type.indexOf("Int")!=-1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					}
				}
				objList.add(t);
			}
			return objList;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return objList;
		
	}
}