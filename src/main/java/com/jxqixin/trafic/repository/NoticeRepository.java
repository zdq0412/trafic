package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Law;
import com.jxqixin.trafic.model.Notice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface NoticeRepository<ID extends Serializable> extends CommonRepository<Notice,ID> {
    /**
     * 查询非企业用户发文的最大发文字号
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m006_notice where org_id is null")
    String findMaxNumWhereOrgIdIsNull();
    /**
     * 根据企业id查找最大发文字号
     * @param orgId
     * @return
     */
    @Query(nativeQuery = true,value = "select max(num) from m006_notice where org_id=?1")
    String findMaxNumByOrgId(String orgId);
    /**
     * 根据法律法规ID查找
     * @param lawId
     * @return
     */
    @Query(nativeQuery = true,value="select * from m006_notice where law_id=?1")
    List<Notice> findByLawId(String lawId);
    /**
     * 根据安全规章制度ID查找
     * @param rulesId
     * @return
     */
    @Query(nativeQuery = true,value="select * from m006_notice where rules_id=?1")
    List<Notice> findByRulesId(String rulesId);

    /**
     * 将公司制度ID设置为null
     * @param id 公司制度ID
     */
    @Modifying
    @Query(nativeQuery = true,value = "update m006_notice set rules_id=null where rules_id=?1")
    void updateRulesId2Null(String id);

    /**
     * 将法律规章ID设置为null
     * @param id 法律规章ID
     */
    @Modifying
    @Query(nativeQuery = true,value = "update m006_notice set law_id=null where law_id=?1")
    void updateLawId2Null(String id);
}
