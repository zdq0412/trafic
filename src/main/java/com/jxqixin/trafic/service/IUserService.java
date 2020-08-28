package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.UserDto;
import com.jxqixin.trafic.model.Org;
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
	public List<Object[]> queryFunctionsByUsername(String username);
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
	 * 根据角色id查找用户数
	 * @param id
	 * @return
	 */
    Integer findCountByRoleId(String id);
	/**
	 * 查找当前组织机构下的用户
	 * @param userDto
	 * @return
	 */
    Page findUsers(UserDto userDto, Org org);
	/**
	 * 根据用户名和企业id查找用户信息
	 * @param username
	 * @param orgId
	 * @return
	 */
	User queryUserByUsernameAndOrgId(String username, String orgId);

	/**
	 * 查找激活用户
	 * @param username
	 * @return
	 */
    User queryActiveUserByUsername(String username);
}
