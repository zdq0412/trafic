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
    /**
     * 根据用户名查找权限
     * @param currentUsername
     * @return
     */
    List<Functions> findFunctions(String currentUsername);
    /**
     * 根据角色ID查找权限ID
     * @param roleId 角色ID
     * @return List<String>
     */
    List<String> findIdsByRoleId(String roleId);
    /**
     * 为角色赋权限
     * @param functionIdArray
     * @param roleId
     */
    void assign2Role(String[] functionIdArray, String roleId);
    /**
     * 根据目录ID查找菜单
     * @param dirId
     * @return
     */
    List<String> findIdsByDirectoryId(String dirId);
    /**
     * 查找所有菜单
     * @return
     */
    List<Functions> findAllMenus();
    /**
     * 查询所有权限，应用于树形结构
     * @return
     */
    List<Functions> findAllFunctions();
    /**
     * 根据企业类别查找权限ID
     * @param orgCategoryId
     * @return
     */
    List<String> findIdsByOrgCategoryId(String orgCategoryId);
    /**
     * 为企业类别赋权限
     * @param functionIdArray
     * @param orgCategoryId
     */
    void assign2OrgCategory(String[] functionIdArray, String orgCategoryId);
    /**
     * 查找超级管理员的权限
     * @return
     */
    List<Functions> findAdminRoleFunctions();
}
