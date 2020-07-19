package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Rules;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface RulesRepository<ID extends Serializable> extends CommonRepository<Rules,ID> {
    /**
     * 查询非企业用户发文的最大发文字号
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m005_rules where org_id is null")
    String findMaxNumWhereOrgIdIsNull();
    /**
     * 根据企业id查找最大发文字号
     * @param orgId
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m005_rules where org_id=?1")
    String findMaxNumByOrgId(String orgId);
}
