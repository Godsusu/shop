package cn.edu.guet.product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.lanqiao.util.PageModel;
import org.lanqiao.util.TransactionHandle;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cn.edu.guet.exception.DaoException;
import cn.edu.guet.ioc.BeanFactory;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class ProductController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//private static Logger logger=Logger.getLogger(ProductController.class);
	
	public String listProduct(HttpServletRequest request, HttpServletResponse response){
		return "product/viewProduct.html";
	}
	
	public void viewProduct(HttpServletRequest request, HttpServletResponse response){
		try {
			Gson gson=new GsonBuilder()
					.setDateFormat("yyyy-MM-dd")
					.create();
			IProductService productService=(IProductService) BeanFactory.getInstance().getBean("productService");
			PageModel<Product> pm=productService.getAllProduct(1);
			response.setContentType("application/json;charset=GBK");
			PrintWriter out=response.getWriter();
			out.write(gson.toJson(pm));
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(HttpServletRequest request, HttpServletResponse response){
		try {
			String productId=request.getParameter("productid");
			IProductService productService=(IProductService) new TransactionHandle().createProxyObject(new ProductServiceImpl());
			System.out.println(productId);
			productService.deleteProduct(productId);
			
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out=response.getWriter();
			out.write("删除成功");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			/**
			 * 返回信息给界面
			 */
			e.printStackTrace();
		}
		
	}
	
	public void updateProductInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			Product product=new Product();
			Map<String,String[]> map=request.getParameterMap();
			BeanUtils.populate(product,map);
			IProductService productService=(IProductService) new TransactionHandle().createProxyObject(new ProductServiceImpl());
			productService.updateProduct(product);
			
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out=response.getWriter();
			out.write("修改成功");
			out.flush();
			out.close();
		} catch (IOException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		} catch (DaoException e) {
			/**
			 * 返回信息给界面
			 */
			e.printStackTrace();
		}
	}
	
	public void addProductInfo(HttpServletRequest request, HttpServletResponse response){
		String realPath = this.getServletContext().getRealPath("/upload");
		Product pro=new Product();
		pro.setProductId(UUID.randomUUID().toString().replace("-", ""));
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// 检查输入请求是否为multipart表单数据。
		
		if (isMultipart == true) {//表示表单要上传文件
			FileItemFactory factory = new DiskFileItemFactory();// 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<FileItem> itr = items.iterator();//items集合中包含了（商品名称、要上传的文件图片）			
			try {
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					if (item.isFormField()) {// 如果是普通表单项目，显示表单内容。
						String fieldName = item.getFieldName();
						if (fieldName.equals("name")) {
							String name=new String(item.getString().getBytes("ISO-8859-1"),"GBK");//经过了TOMCAT把网页上传上来的信息重新编码为ISO-8859-1
							pro.setName(name);//蓝月亮洗手液
						}else if(fieldName.equals("price")){
							pro.setPrice(Float.parseFloat(item.getString()));
						}else if(fieldName.equals("onlineDate")){
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
							Date date=sdf.parse(item.getString());
							pro.setOnlineDate(new java.sql.Date(date.getTime()));
						}else if(fieldName.equals("descInfo")){
							String descInfo=new String(item.getString().getBytes("ISO-8859-1"),"GBK");
							pro.setDescInfo(descInfo);
						}
					} else {// 如果是上传文件，显示文件名。
						File fullFile = new File(item.getName());
						
						System.out.println("上传的文件名："+item.getName());
						File savedFile = new File(realPath + "\\", fullFile.getName());
						item.write(savedFile);
						pro.setPicurl(item.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		// 把文件对象的信息存入数据库
		IProductService productService=(IProductService) new TransactionHandle().createProxyObject(new ProductServiceImpl());
		try {
			productService.saveProduct(pro);
		} catch (DaoException e) {
			/**
			 * 返回信息给界面
			 */
			e.printStackTrace();
		}		
		} else {
			System.out.print("the enctype must be multipart/form-data");
		}
	}
}
