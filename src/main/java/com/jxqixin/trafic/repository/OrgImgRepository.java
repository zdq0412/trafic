package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.OrgImg;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface OrgImgRepository<ID extends Serializable> extends CommonRepository<OrgImg,ID> {
    /**
     * 根据企业ID查找企业图片
     * @param orgId 企业ID
     * @return
     */
    @Query("select oi from OrgImg oi where oi.org.id=?1")
    List<OrgImg> findByOrgId(String orgId);
}
