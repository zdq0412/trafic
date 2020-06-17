package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.RoleFunctions;

public interface IRoleFunctionsService extends ICommonService<RoleFunctions> {
    /**
     * 根据角色id和权限url查找关联对象
     * @param id
     * @param FunctionsUrl
     * @return
     */
    RoleFunctions findByRoleIdAndFunctionsUrl(String id, String FunctionsUrl);
    void insert(String id, String FunctionsUrl);
    void deleteByRoleId(String roleId);
}
