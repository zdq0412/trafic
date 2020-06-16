package com.jxqixin.trafic.service;

import com.twostep.resume.model.RolePower;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface IRolePowerService extends ICommonService<RolePower> {
    /**
     * 根据角色id和权限url查找关联对象
     * @param id
     * @param powerUrl
     * @return
     */
    RolePower findByRoleIdAndPowerUrl(String id, String powerUrl);
    void insert(String id, String powerUrl);
    void deleteByRoleId(String roleId);
}
