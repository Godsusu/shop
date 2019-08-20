package cn.edu.guet.dao;

import java.util.List;

import org.lanqiao.util.PageModel;

public interface IBaseDao<T> {
	void save(T t);
	<O> T getById(O id);//ʹ�÷��ͷ���
	PageModel<T> selectAll(int currentPage);
	int getTotalRows();
	List<T> getAll();//��ȡ���У����ǲ���ҳ
	<E> int delete(E id);
}