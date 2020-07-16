package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface OrgRepository<ID extends Serializable> extends CommonRepository<Org,ID> {
    /**
     * 根据企业类别ID查找企业数量
     * @param id 企业类别ID
     * @return Long  企业数量
     */
    @Query(nativeQuery = true,value="select count(o.id) from org o inner join org_Category oc " +
            " on oc.id=o.org_category_id where oc.id=?1")
    Long findCountByOrgCategoryId(String id);
    /**
     * 根据名称查找企业
     * @param name
     * @return
     */
    Org findByName(String name);

    Org findByCode(String code);
    @Query(nativeQuery = true,value = "select fourColorPicUrl from org where id=?1")
    String findFourColorPic(String id);
}
