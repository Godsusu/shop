package org.lanqiao.util;

import java.lang.reflect.InvocationHandler;
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
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Connection conn=DBConnection.getConn();
		conn.setAutoCommit(false);
		Object retValue=method.invoke(targetObject, args);
		conn.commit();
		return retValue;
	}

}
