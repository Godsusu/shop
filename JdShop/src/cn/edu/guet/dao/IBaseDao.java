package cn.edu.guet.dao;

import java.util.List;

import org.lanqiao.util.PageModel;

public interface IBaseDao<T> {
	void save(T t);
	<O> T getById(O id);//使用泛型方法
	PageModel<T> selectAll(int currentPage);
	int getTotalRows();
	List<T> getAll();//获取所有，但是不分页
	<E> int delete(E id);
}