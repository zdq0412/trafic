package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.OrgDoc;
import org.springframework.data.domain.Page;

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
}
