package cn.edu.guet.ioc;


import java.io.InputStream;
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
	static{
		try {
			SAXReader reader=new SAXReader();
			InputStream in=Class.forName("cn.edu.guet.ioc.BeanFactory").getResourceAsStream("/applicationContext.xml");
			Document doc=reader.read(in);
			List<Element> list=doc.selectNodes("/beans/bean");
			for(Element ele:list){	
				map.put(ele.attributeValue("id"),Class.forName(ele.attributeValue("class")).newInstance());
			}
		} catch (ClassNotFoundException | DocumentException | InstantiationException | IllegalAccessException e) {
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
