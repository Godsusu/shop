package cn.edu.guet.service;

import org.lanqiao.util.PageModel;

import cn.edu.guet.bean.Product;

public interface IProductService {
	PageModel<Product> getAllProduct(int currentPage);
}
