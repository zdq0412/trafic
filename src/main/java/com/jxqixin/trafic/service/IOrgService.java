package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IOrgService extends ICommonService<Org> {
    /**
     * 分页查找企业信息
     * @param nameDto
     * @return
     */
    Page findOrgs(NameDto nameDto);
    /**
     * 根据企业名称查找
     * @param name
     * @return
     */
    Org findByName(String name);
    /**
     * 根据ID删除企业
     * @param id
     */
    void deleteById(String id);
    /**
     * 根据企业代码查找企业信息
     * @param code
     * @return
     */
    Org findByCode(String code);
    /**
     * 新增企业信息
     * @param org
     */
    void addOrg(Org org);

    /**
     * 根据企业ID查找企业四色图访问路径
     * @param id
     * @return
     */
    String findFourColorPic(String id);
}
