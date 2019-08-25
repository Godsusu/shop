package org.lanqiao.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionHandle implements InvocationHandler {
	Object targetObject=new Object();
	
	public Object createProxyObject(Object targetObject){
		this.targetObject=targetObject;
		return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
				targetObject.getClass().getInterfaces(),
				this);
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args)throws Throwable{
		Object retValue=null;
		Connection conn=null;
		try {
			conn=DBConnection.getConn();
			conn.setAutoCommit(false);
			retValue=method.invoke(targetObject, args);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(e instanceof InvocationTargetException){
				conn.rollback();
				throw ((InvocationTargetException) e).getTargetException();
			}
		} 
		return retValue;
	}

}
