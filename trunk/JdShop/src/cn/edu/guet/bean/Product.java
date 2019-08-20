package cn.edu.guet.bean;

import java.sql.Date;

public class Product {
	private String productId;
	private String categoryId;
	private String name;
	private Float price;
	private Date   onLineDate;
    private String picUrl;              
    private String isJingxuan;           
    private String isRemai;              
    private String isXiajia;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCategoryid() {
		return categoryId;
	}
	public void setCategoryid(String categoryid) {
		this.categoryId = categoryid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Date getOnLineDate() {
		return onLineDate;
	}
	public void setOnLineDate(Date onLineDate) {
		this.onLineDate = onLineDate;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getIsJingxuan() {
		return isJingxuan;
	}
	public void setIsJingxuan(String isJingxuan) {
		this.isJingxuan = isJingxuan;
	}
	public String getIsRemai() {
		return isRemai;
	}
	public void setIsRemai(String isRemai) {
		this.isRemai = isRemai;
	}
	public String getIsXiajia() {
		return isXiajia;
	}
	public void setIsXiajia(String isXiajia) {
		this.isXiajia = isXiajia;
	}  
     
     
}
