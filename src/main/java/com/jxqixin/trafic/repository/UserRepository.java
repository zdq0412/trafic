package com.jxqixin.trafic.repository;
import com.twostep.resume.model.Power;
import com.twostep.resume.model.User;
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
    @Query(value = "select distinct p.* from Power p  " +
            "inner join ROLE_POWER rp on p.url = rp.POWER_ID " +
            " inner join Role r  on r.id=rp.role_id " +
            "inner join T_user u on r.id=u.role_id  where u.username=?1",nativeQuery = true)
    public List<Object[]> queryPowersByUsername(String username);
    @Query(value="select u from User u where username=?1")
    public User findByUsername(String username);
    /**根据角色id查找用户*/
    @Query("select u from User u where u.role.roleName=?1")
    public List<User> queryByRoleId(String roleName);
    @Query(nativeQuery = true,value = "select count(id) from T_user where ROLE_ID=?1")
    Integer findCountByRoleId(String id);
}
