package cn.edu.guet.shoppingcart;

public class ShoppingCart {
	private String productid;
	private String customerid;
	private String productnum;
	private String shoppingcartid;
	private String ischoose;
	
	
	public String getShoppingcartid() {
		return shoppingcartid;
	}
	public void setShoppingcartid(String shoppingcartid) {
		this.shoppingcartid = shoppingcartid;
	}
	public String getIschoose() {
		return ischoose;
	}
	public void setIschoose(String ischoose) {
		this.ischoose = ischoose;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getProductnum() {
		return productnum;
	}
	public void setProductnum(String productnum) {
		this.productnum = productnum;
	}
	public String getCustomerid() {
		return customerid;
	}
	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}
	
}
