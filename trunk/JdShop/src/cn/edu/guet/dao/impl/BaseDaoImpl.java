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
		// ƴ���ֶ�˳�� insert into table name(id,name,email,
		while (iter.hasNext()) {
			Method method = iter.next();
			try {
				if (method.invoke(t, new Object[] {}) != null) {// ��ʵ������е�null�������ų�����
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
							if (method.invoke(t, new Object[] {}) != null) {// ��ʵ������е�null�������ų�����
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

		// ȥ�����һ��,����insert insert into table name(id,name,email) values(
		sql = sql.substring(0, sql.lastIndexOf(",")) + ") values(";

		// ƴװԤ����SQL���insert insert into table name(id,name,email) values(?,?,?,
		for (int j = 0; j < i; j++) {
			sql += "?,";
		}

		// ȥ��SQL������һ��,����insert insert into table name(id,name,email)
		// values(?,?,?);
		sql = sql.substring(0, sql.lastIndexOf(",")) + ")";

		// ����SQL���ƴ�����,��ӡSQL���
		System.out.println("�Զ����ɵ�SQL��䣺" + sql);

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

	// �õ����еķ�����������get����set�أ���
	private List<Method> matchPojoMethods(T entity, String methodName) {
		// ��õ�ǰPojo���з�������
		Method[] methods = entity.getClass().getDeclaredMethods();

		// List����������д�get�ַ�����Method����
		List<Method> list = new ArrayList<Method>();

		// ���˵�ǰPojo�����д�get�ַ�����Method����,����List����
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
			if (rs.next()) {// �ж��α��Ƿ��ܹ������ƶ�
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
		// rs.getString(����)
		// rs.getInt(����)
		/*
		 * 1����̬�������� 2����̬���÷���
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
			while (rs.next()) {// �ж��α��Ƿ��ܹ������ƶ�
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
			pm.setList(objList);//����
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
		// rs.getString(����)
		// rs.getInt(����)
		/*
		 * 1����̬�������� 2����̬���÷���
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
			int totalRows = rs.getInt(1);// ��ȡ��һ��
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
			while (rs.next()) {// �ж��α��Ƿ��ܹ������ƶ�
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
		// rs.getString(����)
		// rs.getInt(����)
		/*
		 * 1����̬�������� 2����̬���÷���
		 */
		return null;
	}

	@Override
	public <E> int delete(E id) {
		try {
			String tableName = persistentClass.getSimpleName().toLowerCase();
			String sql="DELETE FROM "+tableName+" WHERE "+tableName+"id=?";
			conn.setAutoCommit(false);//��������
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
		return 0;//û��ʵ�����壬ֻ�Ǳ�֤������������
	}
}