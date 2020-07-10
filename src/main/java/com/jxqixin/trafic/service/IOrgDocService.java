package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgDoc;
import com.jxqixin.trafic.model.OrgImg;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrgDocService extends ICommonService<OrgDoc> {
    /**
     * 根据资质文件ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 根据资质证书名称分页查询
     * @param nameDto
     * @return
     */
    Page findOrgDocs(NameDto nameDto);
    /**
     * 查询企业的资质文件
     * @param org
     * @return
     */
    List<OrgDoc> findAll(Org org);
}
