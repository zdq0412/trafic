package com.jxqixin.trafic.service;

import com.twostep.resume.dto.RoleDto;
import com.twostep.resume.model.Role;
import org.springframework.data.domain.Page;

public interface IRoleService extends ICommonService<Role> {
    /**
     * 根据条件查找角色信息
     * @param roleDto
     * @return
     */
    Page<Role> findByPage(RoleDto roleDto);
    /**
     * 批量删除角色
     * @param ids
     */
    void deleteBatch(String[] ids);

    /**
     * 根据id删除
     * @param id
     */
    void deleteById(String id);

    /***
     * 根据角色名称查找角色
     * @param rolename
     * @return
     */
    Role queryRoleByRolename(String rolename);

    /**
     * 为角色赋权限
     * @param roleName
     * @param powerUrls
     */
    void asignPowers(String roleName, String[] powerUrls);
}
