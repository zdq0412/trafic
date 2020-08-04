package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.OtherDocumentDto;
import com.jxqixin.trafic.model.OtherDocument;
import org.springframework.data.domain.Page;

public interface IOtherDocumentService extends ICommonService<OtherDocument> {
    /**
     * 分页查询信息
     * @param otherDocumentDto
     * @return
     */
    Page findOtherDocuments(OtherDocumentDto otherDocumentDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
