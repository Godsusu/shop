package cn.edu.guet.base;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.util.PageModel;

public interface IBaseDao<T> {
	void save(T t) throws SQLException;
	void update(T t) throws SQLException;
	<O> int delete(O t) throws SQLException;
	<O> T getById(O id) throws SQLException;
	PageModel<T> selectAll(int currentPage) throws SQLException;
	List<T> selectAll() throws SQLException;  //获取所有但不分页
	int getTotalRows() throws SQLException;
}