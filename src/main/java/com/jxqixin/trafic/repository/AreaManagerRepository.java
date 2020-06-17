package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.AreaManager;
import org.springframework.data.jpa.repository.Query;
import java.io.Serializable;
/**
 * 区域管理员数据仓库
 * @param <ID>
 */
public interface AreaManagerRepository<ID extends Serializable> extends CommonRepository<AreaManager,ID> {
    /**
     * 根据用户名密码查询区域管理员
     * @param username
     * @param password
     * @return
     */
    @Query("select u from AreaManager u where username=?1 and password=?2")
    AreaManager findByUsernameAndPassword(String username, String password);
    @Query(value="select u from AreaManager u where username=?1")
    public AreaManager findByUsername(String username);
}
