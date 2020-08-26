package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.SecurityBuildDto;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.SecurityBuild;
import org.springframework.data.domain.Page;
public interface ISecurityBuildService extends ICommonService<SecurityBuild> {
    /**
     * 分页查询信息
     * @param securityBuildDto
     * @return
     */
    Page findSecurityBuilds(SecurityBuildDto securityBuildDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
