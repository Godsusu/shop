package cn.edu.guet.shoppingcart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.util.ProductModel;
import org.lanqiao.util.TransactionHandle;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.product.IProductService;
import cn.edu.guet.product.Product;
import cn.edu.guet.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class ShoppingCartController
 */
public class ShoppingCartController extends BaseServlet {
	private static final long serialVersionUID = 1L;
  
	
	public void addToShoppingCart(HttpServletRequest request, HttpServletResponse response){
			String productId=request.getParameter("productId");
			String productNumber=request.getParameter("productNumber");
			ShoppingCart s=new ShoppingCart();
			s.setProductid(productId);
			s.setProductnum(productNumber);
			s.setCustomerid("0dd142a4992147a99dd93013908cefb5");
			IShoppingCartService scs=(IShoppingCartService) new TransactionHandle().createProxyObject(new ShoppingCartServiceImpl());
			//scs.addProductToCart(s);
	
	}
	
	public void payProduct(HttpServletRequest request, HttpServletResponse response){
		
	}
	
    public void showShoppingCart(HttpServletRequest request, HttpServletResponse response){
    	response.setContentType("application/json;charset=gbk");
    	try {
			String customerId="0dd142a4992147a99dd93013908cefb5";
			IShoppingCartService scs=(IShoppingCartService) new TransactionHandle().createProxyObject(new ShoppingCartServiceImpl());
			List<ShoppingCart> list1=scs.selectShoppingCart(customerId);
			IProductService productService=(IProductService) BeanFactory.getInstance().getBean("productService");
			List<ProductModel> list=new ArrayList<ProductModel>();
			Iterator iter=list1.iterator();
			while(iter.hasNext()){
				ShoppingCart shpoingcart=(ShoppingCart) iter.next();
				Product product=productService.getOneProduct(shpoingcart.getProductid());
				ProductModel pm=new ProductModel();
				pm.setProduct(product);
				pm.setProductNum(shpoingcart.getProductnum());
				list.add(pm);
			}
			PrintWriter out=response.getWriter();
			out.write(JSON.toJSONString(list));
			out.flush();
			out.close();
		} catch (DaoException | IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
