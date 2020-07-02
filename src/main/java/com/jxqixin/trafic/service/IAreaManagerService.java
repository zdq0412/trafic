package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.AreaManager;
import org.springframework.data.domain.Page;
/**
 * 区域管理员业务接口
 */
public interface IAreaManagerService extends ICommonService<AreaManager> {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public AreaManager login(String username, String password);
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public AreaManager queryAreaManagerByUsername(String username);
	/**
	 * 分页查询用户信息
	 * @param userDto
	 * @return
	 */
    Page<AreaManager> findByPage(UserDto userDto);
	/**
	 * 批量删除用户
	 * @param ids
	 */
	void deleteBatch(String[] ids);
	/**
	 * 根据id删除
	 * @param id
	 */
	void deleteById(String id);
	/**
	 * 分页查找区域管理员
	 * @param nameDto
	 * @return
	 */
    Page findAreaManagers(NameDto nameDto);
	/**
	 * 根据用户名查找
	 * @param username
	 * @return
	 */
	AreaManager findByUsername(String username);
}
