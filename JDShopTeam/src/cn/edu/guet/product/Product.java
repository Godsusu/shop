package cn.edu.guet.product;

//import java.util.Date;

import java.sql.Date;

public class Product {
	private String productId;
	private String categoryId;
	private String name;
	private Float price;
	private Date onlineDate;
	private String descInfo;
	private String picurl;
	private String isJingXuan;
	private String isReMai;
	private String isXiaJia;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	public Date getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getDescInfo() {
		return descInfo;
	}
	public void setDescInfo(String descInfo) {
		this.descInfo = descInfo;
	}
	public String getPicurl() {
		return picurl;
	}
	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}
	public String getIsJingXuan() {
		return isJingXuan;
	}
	public void setIsJingXuan(String isJingXuan) {
		this.isJingXuan = isJingXuan;
	}
	public String getIsRemai() {
		return isReMai;
	}
	public void setIsRemai(String isReMai) {
		this.isReMai = isReMai;
	}
	public String getIsXiaJia() {
		return isXiaJia;
	}
	public void setIsXiaJia(String isXiaJia) {
		this.isXiaJia = isXiaJia;
	}
}
