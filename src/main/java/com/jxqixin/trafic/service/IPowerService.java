package com.jxqixin.trafic.service;

import com.twostep.resume.model.Power;

import java.util.List;

public interface IPowerService extends ICommonService<Power> {
    /**
     * 查找最顶层权限
     * @return
     */
    List<Power> queryTopPower();
    /**
     * 根据父url查找子权限
     * @param url
     * @return
     */
    List<Power> findByParentUrl(String url);
    /**
     * 根据角色名称查找权限
     * @param roleName
     * @return
     */
    List<Power> queryByRoleName(String roleName);
}
