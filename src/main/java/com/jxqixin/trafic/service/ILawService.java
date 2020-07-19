package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Law;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;
public interface ILawService extends ICommonService<Law> {
    /**
     * 分页查询模式信息
     * @param nameDto
     * @return
     */
    Page findLaws(NameDto nameDto,Org org);
    /**
     * 根据ID删除模式，同时删除模式下的所有目录
     */
    void deleteById(String id);
    /**
     * 新增法律法规文件
     * @param law 法律法规文件对象
     * @param org 企业对象
     */
    void addLaw(Law law, Org org);
}
