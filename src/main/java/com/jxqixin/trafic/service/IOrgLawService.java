package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgLaw;
import org.springframework.data.domain.Page;

public interface IOrgLawService extends ICommonService<OrgLaw> {
    /**
     * 分页查找法律法规文件
     * @param nameDto
     * @param org
     * @return
     */
    Page findOrgLaws(NameDto nameDto, Org org);

    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 发布通知
     * @param id orgLawID
     */
    void publishLaw(String id);
}
