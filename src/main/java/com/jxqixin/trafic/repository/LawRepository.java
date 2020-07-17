package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Law;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface LawRepository<ID extends Serializable> extends CommonRepository<Law,ID> {
    /**
     * 查询非企业用户发文的最大发文字号
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m004_law where org_id is null")
    String findMaxNumWhereOrgIdIsNull();
    /**
     * 根据企业id查找最大发文字号
     * @param orgId
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m004_law where orgId=?1")
    String findMaxNumByOrgId(String orgId);
}
