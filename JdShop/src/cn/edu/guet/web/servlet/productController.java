package cn.edu.guet.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lanqiao.util.PageModel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.log4j.Logger;

import cn.edu.guet.bean.Permission;
import cn.edu.guet.bean.Product;
import cn.edu.guet.service.IPermissionService;
import cn.edu.guet.service.IProductService;
import cn.edu.guet.service.impl.PermissionServiceImpl;
import cn.edu.guet.service.impl.ProductServiceImpl;
import cn.edu.guet.web.servlet.base.BaseServlet;

/**
 * Servlet implementation class productController
 */
public class productController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(productController.class);

    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	return "product/addProduct.html";
    }
    
    public void viewProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
			/**
			 * 构建业务层的对象，然后调用getAllProduct方法
			 */
			Gson gson = new GsonBuilder()  
					  .setDateFormat("yyyy-MM-dd")  
					  .create();  
			IProductService productService = new ProductServiceImpl();
			PageModel<Product> pm = productService.getAllProduct(1);
			System.out.println(gson.toJson(pm));
			logger.debug(gson.toJson(pm));
			
			
			response.setContentType("application/json;charset=GBK");
			
			PrintWriter out = response.getWriter();
			out.write(gson.toJson(pm));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
