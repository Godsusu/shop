package cn.edu.guet.ioc;


import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BeanFactory {
	/**
	 * 饿汉模式：自己建立对象
	 */
	private static BeanFactory instance=new BeanFactory();
	static Map<String,Object> map=new HashMap<String,Object>();
	
	
	public static void parseElement(Element ele){
		try {
			String id=ele.attributeValue("id");
			Class c=Class.forName(ele.attributeValue("class"));
			Object beanObj=c.newInstance();
			map.put(id,beanObj);
			
			String ref="";
			Object obj=null;
			List<Element> childElement=ele.elements();
			for(Element childEle:childElement){
				ref=childEle.attributeValue("ref");
				obj = map.get(ref);
				if(obj==null){
					for(Element el:list){
						String ids=el.attributeValue("id");
						if(ids.equals(ref)){
							parseElement(el);
						}
					}
				}
				obj=map.get(ref);
				Method[] methods=c.getDeclaredMethods();
				for(Method method:methods){
					if(method.getName().startsWith("set") && method.getName().toLowerCase().contains(ref.toLowerCase())){
						method.invoke(beanObj, obj);
					}
				}
			}
			
				
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	static List<Element> list;
	static{
		try {
			SAXReader reader=new SAXReader();
			InputStream in=Class.forName("cn.edu.guet.ioc.BeanFactory").getResourceAsStream("/applicationContext.xml");
			Document doc=reader.read(in);
			list=doc.selectNodes("/beans/bean");
			for(Element ele:list){	
				parseElement(ele);
			}
		} catch (ClassNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
	}
	
	private BeanFactory(){
		
	}
	public static BeanFactory getInstance(){	
		return instance;
	}
	public Object getBean(String id){
		return map.get(id);
	}
}
