package org.lanqiao.util;

import java.util.List;

public class PageModel<T> {

	private int totalPage;
	private List<T> list;
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
}
