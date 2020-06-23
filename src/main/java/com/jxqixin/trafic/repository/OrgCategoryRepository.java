package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.OrgCategory;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface OrgCategoryRepository<ID extends Serializable> extends CommonRepository<OrgCategory,ID> {
    /**
     * 根据当前登录用户查找其所在企业类别
     * @param username 当前登录用户名
     * @return
     */
    @Query(nativeQuery = true,value="select oc.* from org_category oc " +
            " inner join org o on oc.id=o.org_category_id  " +
            " inner join t_user u on u.org_id=o.id where u.username=?1")
    OrgCategory findByUsername(String username);
}
