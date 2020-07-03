package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Role;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRoleService extends ICommonService<Role> {
    /**
     * 根据角色名称和企业ID查找角色
     * @param name
     * @param orgId
     * @return
     */
    Role findByNameAndOrgId(String name,String orgId);

    /**
     * 分页查找角色
     * @param nameDto
     * @param orgId
     * @return
     */
    Page findRoles(NameDto nameDto,String orgId);
    /**
     * 根据ID删除角色
     * @param id
     */
    void deleteById(String id);
    /**
     * 根据企业id查找角色信息
     * @param orgId
     * @return
     */
    List<Role> findAllRoles(String orgId);
}
