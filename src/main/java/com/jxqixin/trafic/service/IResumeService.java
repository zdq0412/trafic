package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.ResumeDto;
import com.jxqixin.trafic.model.Resume;
import org.springframework.data.domain.Page;

public interface IResumeService extends ICommonService<Resume> {
    /**
     * 分页查询模式信息
     * @param resumeDto
     * @return
     */
    Page findResumes(ResumeDto resumeDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
