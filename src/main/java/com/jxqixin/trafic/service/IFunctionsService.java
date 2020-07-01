package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.Menus;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Functions;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IFunctionsService extends ICommonService<Functions> {
    /**
     * 根据角色名称查找权限
     * @param roleName
     * @return
     */
    List<Functions> queryByRoleName(String roleName);
    /**
     * 根据用户名查找显示菜单
     * @param username
     * @return
     */
    List<Menus> findMenus(String username);
    /**
     * 分页查询菜单
     * @param nameDto
     * @return
     */
    Page findMenusByPage(NameDto nameDto);
    /**
     * 根据名称查找权限
     * @param name
     * @return
     */
    Functions findByName(String name);
    /**
     * 新增权限
     * @param functions
     */
    void addFunction(Functions functions);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 根据菜单ID查找菜单下的功能
     * @param nameDto
     * @return
     */
    Page findFunctionsByPage(NameDto nameDto);
}
