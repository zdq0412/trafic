package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.User;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;
public interface UserRepository<ID extends Serializable> extends CommonRepository<User,ID> {
    /**
     * 根据用户名密码查询用户
     * @param username
     * @param password
     * @return
     */
    @Query("select u from User u where username=?1 and password=?2")
    User findByUsernameAndPassword(String username, String password);
    /**
     * 根据用户名查找权限
     * @param username
     * @return
     */
   /* @Query(value = "select distinct p.* from T_USER u " +
            "inner join ROLE r on r.id = u.ROLE_ID " +
            "inner join ROLE_POWER rp on r.id = rp.ROLE_ID " +
            "inner join POWER p on p.url=rp.POWER_ID where u.username=?1",nativeQuery = true)*/
    @Query(value = "select distinct p.* from Functions p  " +
            "inner join ROLE_FUNCTIONS rp on p.id = rp.function_ID " +
            " inner join Role r  on r.id=rp.role_id " +
            "inner join T_user u on r.id=u.role_id  where u.username=?1",nativeQuery = true)
    public List<Object[]> queryFunctionsByUsername(String username);
    //@Query(value="select u.* from T_User u where username=?1 and u.org_id is null",nativeQuery = true)
    public User findByUsername(String username);
    /**
     * 根据用户名查找激活用户
     * @param username
     * @return
     */
    @Query("select u from User u where u.username=?1 and u.status='0'")
    public User findActiveUserByUsername(String username);
    /**根据角色id查找用户*/
    @Query("select u from User u where u.role.id=?1")
    public List<User> queryByRoleId(String id);
    @Query(nativeQuery = true,value = "select count(id) from T_user where ROLE_ID=?1")
    Integer findCountByRoleId(String id);
    @Query(value="select u.* from T_User u where username=?1 and u.org_id=?2",nativeQuery = true)
    User findByUsernameAndOrgId(String username, String orgId);
}
