import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.customer.Customer;
import cn.edu.guet.customer.ICustomerService;
import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.product.IProductService;
import cn.edu.guet.product.Product;

public class Text {
	
	
	@Test
	public void getOneProduct(){
		String id="0dd142a4992147a99dd93013908cefb5";
		try {
			ICustomerService customerService=(ICustomerService) BeanFactory.getInstance().getBean("customerService");
			Customer customer=customerService.selectCustomer(id);
			System.out.println(JSON.toJSON(customer));
		} catch (DaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
