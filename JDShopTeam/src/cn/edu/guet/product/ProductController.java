package cn.edu.guet.product;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import cn.edu.guet.web.servlet.base.BaseServlet;

public class ProductController extends BaseServlet {
	private static final long serialVersionUID = 1L;
	//private static Logger logger=Logger.getLogger(ProductController.class);
	
	public String goToProduct(HttpServletRequest request, HttpServletResponse response){
		
		return "product/viewProduct.html";
	}
	
	public void viewProduct(HttpServletRequest request, HttpServletResponse response){
		try {
			Gson gson=new GsonBuilder()
					.setDateFormat("yyyy-MM-dd")
					.create();
			IProductService productService=new ProductServiceImpl();
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
			IProductService productService=new ProductServiceImpl();
			System.out.println(productId);
			productService.deleteProduct(productId);
			
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out=response.getWriter();
			out.write("ɾ���ɹ�");
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateProductInfo(HttpServletRequest request, HttpServletResponse response){
		try {
			Product product=new Product();
			Map<String,String[]> map=request.getParameterMap();
			BeanUtils.populate(product,map);
			IProductService productService=new ProductServiceImpl();
			productService.updateProduct(product);
			
			response.setContentType("text/plain;charset=gbk");
			PrintWriter out=response.getWriter();
			out.write("�޸ĳɹ�");
			out.flush();
			out.close();
		} catch (IOException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	public void addProductInfo(HttpServletRequest request, HttpServletResponse response){
		String realPath = this.getServletContext().getRealPath("/upload");
		Product pro=new Product();
		pro.setProductId(UUID.randomUUID().toString().replace("-", ""));
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);// ������������Ƿ�Ϊmultipart�����ݡ�
		
		if (isMultipart == true) {//��ʾ��Ҫ�ϴ��ļ�
			FileItemFactory factory = new DiskFileItemFactory();// Ϊ�����󴴽�һ��DiskFileItemFactory����ͨ��������������ִ�н��������еı���Ŀ��������һ��List�С�
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator<FileItem> itr = items.iterator();//items�����а����ˣ���Ʒ���ơ�Ҫ�ϴ����ļ�ͼƬ��
			
			
			try {
				while (itr.hasNext()) {
					FileItem item = (FileItem) itr.next();
					// ��鵱ǰ��Ŀ����ͨ����Ŀ�����ϴ��ļ���
					if (item.isFormField()) {// �������ͨ����Ŀ����ʾ�����ݡ�
						String fieldName = item.getFieldName();
						if (fieldName.equals("name")) {
							String name=new String(item.getString().getBytes("ISO-8859-1"),"GBK");//������TOMCAT����ҳ�ϴ���������Ϣ���±���ΪISO-8859-1
							pro.setName(name);//������ϴ��Һ
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
					} else {// ������ϴ��ļ�����ʾ�ļ�����
						File fullFile = new File(item.getName());
						
						System.out.println("�ϴ����ļ�����"+item.getName());
						File savedFile = new File(realPath + "\\", fullFile.getName());
						item.write(savedFile);
						pro.setPicurl(item.getName());
						// System.out.println(savedFile.getAbsolutePath());
						// System.out.println("upload/pic/"+item.getName());
						//pro.setProductPic(item.getName());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		// ���ļ��������Ϣ�������ݿ�
		IProductService productService=new ProductServiceImpl();
		productService.saveProduct(pro);
			
			
			
		} else {
			System.out.print("the enctype must be multipart/form-data");
		}
	}
}
