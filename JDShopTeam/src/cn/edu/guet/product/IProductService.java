package cn.edu.guet.product;


import org.lanqiao.util.PageModel;

public interface IProductService {
	PageModel<Product> getAllProduct(int currentPage);
	void deleteProduct(String productId);
	void saveProduct(Product p);
	void updateProduct(Product product);
}
