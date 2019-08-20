package cn.edu.guet.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.lanqiao.util.DBConnection;
import org.lanqiao.util.PageModel;

import cn.edu.guet.dao.IBaseDao;

public class BaseDaoImpl<T> implements IBaseDao<T> {
	PreparedStatement pstmt;
	Connection conn;
	private Class<T> persistentClass;
	// Class c=Class.forName("Users");

	public BaseDaoImpl() {
		conn = DBConnection.getConn();
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		persistentClass = (Class<T>) type.getActualTypeArguments()[0];
	}

	@Override
	public void save(T t) {
		String sql = "INSERT INTO " + t.getClass().getSimpleName().toLowerCase() + " (";
		List<Method> list = this.matchPojoMethods(t, "get");
		Iterator<Method> iter = list.iterator();
		Object obj[] = new Object[list.size()];
		int i = 0;
		// 拼接字段顺序 insert into table name(id,name,email,
		while (iter.hasNext()) {
			Method method = iter.next();
			try {
				if (method.invoke(t, new Object[] {}) != null) {// 把实体对象中的null的属性排除在外
					sql += method.getName().substring(3).toLowerCase() + ",";

					if (method.getReturnType().getSimpleName().indexOf("Date") != -1) {
						SimpleDateFormat sbf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						try {
							obj[i] = sbf.format(method.invoke(t, new Object[] {}));
						} catch (IllegalAccessException e) {

							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							e.printStackTrace();
						}
					} else {
						try {
							if (method.invoke(t, new Object[] {}) != null) {// 把实体对象中的null的属性排除在外
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

		try {
			pstmt = conn.prepareStatement(sql);
			for (int j = 0; j < i; j++) {
				pstmt.setObject(j + 1, obj[j]);
			}
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		DBConnection.closeConn(conn);
	}

	// 得到所有的方法（到底是get还是set呢？）
	private List<Method> matchPojoMethods(T entity, String methodName) {
		// 获得当前Pojo所有方法对象
		Method[] methods = entity.getClass().getDeclaredMethods();

		// List容器存放所有带get字符串的Method对象
		List<Method> list = new ArrayList<Method>();

		// 过滤当前Pojo类所有带get字符串的Method对象,存入List容器
		for (int index = 0; index < methods.length; index++) {
			if (methods[index].getName().indexOf(methodName) != -1) {
				list.add(methods[index]);
			}
		}
		return list;
	}

	@Override
	public <O> T getById(O id) {
		String className = persistentClass.getSimpleName().toLowerCase();
		String sql = "SELECT * FROM " + className + " WHERE " + className + "id=?";
		System.out.println("sql: " + sql);
		T t = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setObject(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {// 判断游标是否能够向下移动
				t = persistentClass.newInstance();
				List<Method> list = this.matchPojoMethods(t, "set");
				Iterator<Method> iter = list.iterator();
				while (iter.hasNext()) {
					Method method = iter.next();
					String type = method.getParameterTypes()[0].getName();
					if (type.indexOf("String") != -1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					} else if (type.indexOf("int") != -1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					}
				}
			}
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// rs.getString(列名)
		// rs.getInt(列名)
		/*
		 * 1、动态创建对象 2、动态调用方法
		 */
		return null;
	}

	@Override
	public PageModel<T> selectAll(int currentPage) {

		String tableName = persistentClass.getSimpleName().toLowerCase();

		int rowsPerPage = 3;

		int endRow = currentPage * rowsPerPage;
		int startRow = (currentPage - 1) * rowsPerPage + 1;
		int totalRows = getTotalRows();
		int totalPage = 0;
		if (totalRows % rowsPerPage == 0) {
			totalPage = totalRows / rowsPerPage;
		} else {
			totalPage = totalRows / rowsPerPage + 1;
		}

		String sql = "SELECT * FROM (SELECT rownum rn,t.* FROM (SELECT * FROM " + tableName
				+ ") t WHERE rownum<=?) WHERE rn>=?";

		List<T> objList = new ArrayList<T>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, endRow);
			pstmt.setInt(2, startRow);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {// 判断游标是否能够向下移动
				T t = persistentClass.newInstance();
				List<Method> list = this.matchPojoMethods(t, "set");
				Iterator<Method> iter = list.iterator();
				while (iter.hasNext()) {
					Method method = iter.next();
					String type = method.getParameterTypes()[0].getName();
					if (type.indexOf("String") != -1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					} else if (type.indexOf("int") != -1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					} else if (type.indexOf("float") != -1) {
						method.invoke(t, rs.getFloat(method.getName().substring(3).toLowerCase()));
					}
				}
				objList.add(t);
			}
			PageModel<T> pm=new PageModel<T>();
			pm.setList(objList);//数据
			pm.setTotalPage(totalPage);
			return pm;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// rs.getString(列名)
		// rs.getInt(列名)
		/*
		 * 1、动态创建对象 2、动态调用方法
		 */
		return null;
	}

	@Override
	public int getTotalRows() {
		try {
			String tableName = persistentClass.getSimpleName().toLowerCase();
			String sql = "SELECT COUNT(*) FROM " + tableName;
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			int totalRows = rs.getInt(1);// 获取第一列
			return totalRows;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	@Override
	public List<T> getAll() {
		String sql = "SELECT * FROM " + persistentClass.getSimpleName().toLowerCase();
		System.out.println("sql: " + sql);
		List<T> objList = new ArrayList<T>();
		try {
			pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {// 判断游标是否能够向下移动
				T t = persistentClass.newInstance();
				List<Method> list = this.matchPojoMethods(t, "set");
				Iterator<Method> iter = list.iterator();
				while (iter.hasNext()) {
					Method method = iter.next();
					String type = method.getParameterTypes()[0].getName();
					if (type.indexOf("String") != -1) {
						method.invoke(t, rs.getString(method.getName().substring(3).toLowerCase()));
					} else if (type.indexOf("int") != -1) {
						method.invoke(t, rs.getInt(method.getName().substring(3).toLowerCase()));
					} else if (type.indexOf("float") != -1) {
						method.invoke(t, rs.getFloat(method.getName().substring(3).toLowerCase()));
					}
				}
				objList.add(t);
			}
			return objList;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		// rs.getString(列名)
		// rs.getInt(列名)
		/*
		 * 1、动态创建对象 2、动态调用方法
		 */
		return null;
	}

	@Override
	public <E> int delete(E id) {
		try {
			String tableName = persistentClass.getSimpleName().toLowerCase();
			String sql="DELETE FROM "+tableName+" WHERE "+tableName+"id=?";
			conn.setAutoCommit(false);//开启事务
			pstmt=conn.prepareStatement(sql);
			pstmt.setObject(1, id);
			int a=pstmt.executeUpdate();
			if(a==1){
				conn.commit();
				return 1;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
				return 0;
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		DBConnection.closeConn(conn);
		return 0;//没有实际意义，只是保证方法的完整性
	}
}