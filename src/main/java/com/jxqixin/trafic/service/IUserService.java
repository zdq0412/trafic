package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.User;
import org.springframework.data.domain.Page;
import java.util.List;
public interface IUserService extends ICommonService<User> {
	/**
	 * 用户登录
	 * @param username
	 * @param password
	 * @return
	 */
	public User login(String username, String password);
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User queryUserByUsername(String username);
	/**
	 * 根据用户名查找权限
	 * @param username
	 * @return
	 */
	public List<Object[]> queryPowersByUsername(String username);

	/**
	 * 分页查询用户信息
	 * @param userDto
	 * @return
	 */
    Page<User> findByPage(UserDto userDto);

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

	Page<User> findByPageWithoutAdmin(UserDto userDto);
	/**
	 * 根据角色id查找用户数
	 * @param id
	 * @return
	 */
    Integer findCountByRoleId(String id);
}
