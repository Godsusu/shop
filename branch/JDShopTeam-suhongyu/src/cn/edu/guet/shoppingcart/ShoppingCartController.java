package cn.edu.guet.shoppingcart;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.lanqiao.util.ProductModel;
import org.lanqiao.util.TransactionHandle;
import org.lanqiao.util.UUIDString;

import com.alibaba.fastjson.JSON;

import cn.edu.guet.customer.ICustomerService;
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
			
				HttpSession session=request.getSession();
				
				String productId=request.getParameter("productId");
				String productNumber=request.getParameter("productNumber");
				ShoppingCart s=new ShoppingCart();
				s.setProductid(productId);
				s.setProductnum(productNumber);
				s.setCustomerid((String) session.getAttribute("customerid"));
				s.setShoppingcartid(UUIDString.getId());
				IShoppingCartService scs=(IShoppingCartService) new TransactionHandle().createProxyObject(new ShoppingCartServiceImpl());
				
				try {
					scs.addProductToCart(s);
				} catch (DaoException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	
	}
	
	public void payProduct(HttpServletRequest request, HttpServletResponse response){
		
	}
	
    public void showShoppingCart(HttpServletRequest request, HttpServletResponse response){
    	response.setContentType("application/json;charset=gbk");
    	List<ShoppingCart> list=new ArrayList<ShoppingCart>();
		HttpSession session=request.getSession();
		String customerId=(String) session.getAttribute("customerid");
		
		IShoppingCartService shoppingcartService=(IShoppingCartService) BeanFactory.getInstance().getBean("shoppingCartService");
		list=shoppingcartService.selectShoppingCart(customerId);
		List<ProductModel> list1=new ArrayList<ProductModel>();
		Iterator<ShoppingCart> iter=list.iterator();
		System.out.println(JSON.toJSONString(list));
		try {
			while(iter.hasNext()){
				ProductModel pm=new ProductModel();
				ShoppingCart shopping=new ShoppingCart();
				shopping=iter.next();
				pm.setProductNum(shopping.getProductnum());
				pm.setShoppingcartId(shopping.getShoppingcartid());
				IProductService productService=(IProductService) BeanFactory.getInstance().getBean("productService");
				Product product=productService.getOneProduct(shopping.getProductid());
				pm.setProduct(product);
				list1.add(pm);
			}
			System.out.println(JSON.toJSONString(list1));
			PrintWriter out=response.getWriter();
			out.write(JSON.toJSONString(list1));
			out.flush();
			out.close();
		} catch (DaoException | IOException e) {
			e.printStackTrace();
		}
	}
    public void updateShoppingcart(HttpServletRequest request, HttpServletResponse response){
    	String shoppingcartid=request.getParameter("shoppingcartid");
    	String shoppingcartnum=request.getParameter("productNum");
    	ShoppingCart shoppingCart=new ShoppingCart();
    	shoppingCart.setShoppingcartid(shoppingcartid);
    	shoppingCart.setProductnum(shoppingcartnum);
    	shoppingCart.setIschoose("true");
    	IShoppingCartService shoppingcartService=(IShoppingCartService) BeanFactory.getInstance().getBean("shoppingCartService");
    	shoppingcartService.updataShoppingCart(shoppingCart);
    }
	
    public void getInfo(HttpServletRequest request, HttpServletResponse response){
    	response.setContentType("application/json;charset=gbk");
    	IShoppingCartService shoppingcartService=(IShoppingCartService) BeanFactory.getInstance().getBean("shoppingCartService");
    	List<ShoppingCart> list = shoppingcartService.getAllOrder();
    	List<ProductModel> list1=new ArrayList<ProductModel>();
    	Iterator<ShoppingCart> iter=list.iterator();
    	System.out.println(JSON.toJSONString(list));
		try {
			while(iter.hasNext()){
				ProductModel pm=new ProductModel();
				ShoppingCart shopping=new ShoppingCart();
				shopping=iter.next();
				pm.setProductNum(shopping.getProductnum());
				pm.setShoppingcartId(shopping.getShoppingcartid());
				IProductService productService=(IProductService) BeanFactory.getInstance().getBean("productService");
				Product product=productService.getOneProduct(shopping.getProductid());
				pm.setProduct(product);
				list1.add(pm);
			}
			System.out.println(JSON.toJSONString(list1));
			PrintWriter out=response.getWriter();
			out.write(JSON.toJSONString(list1));
			out.flush();
			out.close();
		} catch (DaoException | IOException e) {
			e.printStackTrace();
		}
	}
}
