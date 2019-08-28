import java.io.PrintWriter;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.product.IProductService;
import cn.edu.guet.product.Product;

public class Text {
	
	
	@Test
	public void getOneProduct(){
		try {
			String productId="ed976bcbcf2f4f06a1111013b2434147";
			IProductService productService=(IProductService) BeanFactory.getInstance().getBean("productService");
			Product product=productService.getOneProduct(productId);
			System.out.println(JSON.toJSONString(product));
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	
}
