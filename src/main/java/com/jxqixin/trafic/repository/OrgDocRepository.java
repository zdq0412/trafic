package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.OrgDoc;
import com.jxqixin.trafic.model.OrgImg;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface OrgDocRepository<ID extends Serializable> extends CommonRepository<OrgDoc,ID> {
    /**
     * 根据企业ID查找企业资质文件
     * @param orgId
     * @return
     */
    @Query("select od from OrgDoc od where od.org.id=?1")
    List<OrgDoc> findByOrgId(String orgId);
}
