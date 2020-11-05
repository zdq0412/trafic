package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.QualificationDocumentDto;
import com.jxqixin.trafic.model.QualificationDocument;
import org.springframework.data.domain.Page;

public interface IQualificationDocumentService extends ICommonService<QualificationDocument> {
    /**
     * 分页查询信息
     * @param qualificationDocumentDto
     * @return
     */
    Page findQualificationDocuments(QualificationDocumentDto qualificationDocumentDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 添加资质文件
     * @param qualificationDocument
     */
    void addQualificationDocument(QualificationDocument qualificationDocument);
}
