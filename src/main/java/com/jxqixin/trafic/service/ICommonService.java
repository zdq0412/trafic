package com.jxqixin.trafic.service;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 通用业务接口
 * @param <T>
 */
public interface ICommonService<T> {
	/**
	 * 更新对象
	 * @param obj
	 */
	public void updateObj(T obj);
	/**
	 * 添加对象
	 * @param obj
	 * @return
	 */
	public T addObj(T obj);
	/**
	 * 根据id查询对象
	 * @param id
	 * @return
	 */
	public T queryObjById(Serializable id);
	/**
	 * 根据id删除对象
	 * @param id
	 */
	public void deleteObj(Serializable id);
	/**
	 *  分页查询
	 * @param pageable
	 * @return
	 */
	public Page<T> findAll(Pageable pageable);
	/**
	 * 根据条件分页查询
	 * @param pageable
	 * @param example
	 * @return
	 */
	//public Page<T> findAll(Pageable pageable, Example example);
	/**
	 * 查询所有
	 * @return
	 */
	public List<T> findAll();
}
