package com.jxqixin.trafic.service;
import com.jxqixin.trafic.model.Functions;

import java.util.List;

public interface IFunctionsService extends ICommonService<Functions> {
    /**
     * 查找最顶层权限
     * @return
     */
    List<Functions> queryTopFunctions();
    /**
     * 根据父id查找子权限
     * @param id
     * @return
     */
    List<Functions> findByParentId(String id);
    /**
     * 根据角色名称查找权限
     * @param roleName
     * @return
     */
    List<Functions> queryByRoleName(String roleName);
}
