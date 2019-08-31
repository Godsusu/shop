package cn.edu.guet.order;

import java.sql.Date;

public class Orders {
	private String orderId;
	private String customerId;
	private Float ammount;
	private String status;
	private String buyerInfo;
	private Date orderDate;
	private String cashInfo;
	private String expressInfo;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Float getAmmount() {
		return ammount;
	}
	public void setAmmount(Float ammount) {
		this.ammount = ammount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBuyerInfo() {
		return buyerInfo;
	}
	public void setBuyerInfo(String buyerInfo) {
		this.buyerInfo = buyerInfo;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getCashInfo() {
		return cashInfo;
	}
	public void setCashInfo(String cashInfo) {
		this.cashInfo = cashInfo;
	}
	public String getExpressInfo() {
		return expressInfo;
	}
	public void setExpressInfo(String expressInfo) {
		this.expressInfo = expressInfo;
	}
	
	
	
}
