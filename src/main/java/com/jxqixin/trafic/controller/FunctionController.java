package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.FunctionDto;
import com.jxqixin.trafic.dto.Menus;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.IDirectoryService;
import com.jxqixin.trafic.service.IFunctionsService;
import com.jxqixin.trafic.service.ISchemaService;
import com.jxqixin.trafic.util.RedisUtil;
import com.jxqixin.trafic.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 权限控制器
 */
@RestController
public class FunctionController extends CommonController{
    @Autowired
    private IFunctionsService functionsService;
    @Autowired
    private IDirectoryService directoryService;
    /**
     * 查询用户显示菜单
     * @param request
     * @return
     */
    @GetMapping("/functions/menus")
    public List<Menus> queryMenus(HttpServletRequest request){
           String username = getCurrentUsername(request);
           List<Menus> list = functionsService.findMenus(username);
        return list;
    }
    /**
     * 分页查询模式
     * @param nameDto
     * @return
     */
    @GetMapping("/functions/menusByPage")
    public ModelMap queryMenusByPage(NameDto nameDto){
        Page page = functionsService.findMenusByPage(nameDto);
        return pageModelMap(page);
    }

    /**
     * 分页查询菜单下的功能
     * @param  nameDto name字段为菜单的ID
     * @return
     */
    @GetMapping("/functions/functionsByPage")
    public ModelMap queryFunctionsByPage(NameDto nameDto){
        Page page = functionsService.findFunctionsByPage(nameDto);
        return pageModelMap(page);
    }
    /**
     * 角色授权查询
     * @return
     */
    @GetMapping("functions/roleFunctions")
    public ModelMap findFunctions(String roleId,HttpServletRequest request){
        //查找当前用户下的权限,用户只能分配其具有的权限
        List<Functions> functions = functionsService.findFunctions(getCurrentUsername(request));
        //查找roleId下的权限ID
        List<String> functionsIdList = functionsService.findIdsByRoleId(roleId);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("functions",functions);
        modelMap.addAttribute("functionIds",functionsIdList);
        return modelMap;
    }
    /**
     * 目录授权查询
     * @return
     */
    @GetMapping("/functions/directoryFunctions")
    public ModelMap findDirectoryFunctions(String dirId){
        List<Functions> functions = functionsService.findAllMenus();
        List<String> functionsIdList = functionsService.findIdsByDirectoryId(dirId);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("functions",functions);
        modelMap.addAttribute("functionIds",functionsIdList);
        return modelMap;
    }
    /**
     * 企业类别授权查询
     * @return
     */
    @GetMapping("/functions/orgCategoryFunctions")
    public ModelMap findOrgCategoryFunctions(String orgCategoryId){
        List<Functions> functions = functionsService.findAllFunctions();
        List<String> functionsIdList = functionsService.findIdsByOrgCategoryId(orgCategoryId);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("functions",functions);
        modelMap.addAttribute("functionIds",functionsIdList);
        return modelMap;
    }
    /**
     * 为角色赋权限
     * @param functionsId
     * @param roleId
     * @return
     */
    @PutMapping("/functions/roleFunctions")
    public JsonResult assignFunctions2Role(String functionsId,String roleId){
        String[] functionIdArray = StringUtil.handleIds(functionsId);
        functionsService.assign2Role(functionIdArray,roleId);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 为目录赋权限
     * @param functionsId
     * @param orgCategoryId
     * @return
     */
    @PutMapping("/functions/orgCategoryFunctions")
    public JsonResult assignFunctions2OrgCategory(String functionsId,String orgCategoryId){
        String[] functionIdArray = StringUtil.handleIds(functionsId);
        functionsService.assign2OrgCategory(functionIdArray,orgCategoryId);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 为目录赋菜单
     * @param functionsId
     * @param dirId
     * @return
     */
    @PutMapping("/functions/directoryFunctions")
    public JsonResult assignFunctions2Directory(String functionsId,String dirId){
        String[] functionIdArray = StringUtil.handleIds(functionsId);
        directoryService.assign2Directory(functionIdArray,dirId);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 根据模式名称查找
     * @param name
     * @return
     */
    @GetMapping("/functions/function/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Functions functions = functionsService.findByName(name);
        if(functions==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 新增权限
     * @param functionDto
     * @return
     */
    @PostMapping("/functions/function")
    public JsonResult addFunction(FunctionDto functionDto){
        Functions functions = new Functions();
        BeanUtils.copyProperties(functionDto,functions);
        Functions parent = new Functions();
        parent.setId(functionDto.getParentId());
        parent.setName(functionDto.getParentName());
        parent.setType(functionDto.getParentType());
        functions.setParent(parent);

        JsonResult jsonResult = findByName(functions.getName());
        if(jsonResult.getResult().getResultCode()==200){
            functions.setId(UUID.randomUUID().toString());
            functions.setCreateDate(new Date());
            functionsService.addFunction(functions);
        }
        return jsonResult;
    }

    /**
     * 编辑权限
     * @param functions
     * @return
     */
    @PutMapping("/functions/function")
    public JsonResult updateFunctions(Functions functions){
        Functions s = functionsService.findByName(functions.getName());

        if(s!=null && !s.getId().equals(functions.getId())){
            return new JsonResult(Result.FAIL);
        }
        Functions savedFunctions = functionsService.queryObjById(functions.getId());
        savedFunctions.setName(functions.getName());
        savedFunctions.setIcon(functions.getIcon());
        savedFunctions.setIndex(functions.getIndex());
        savedFunctions.setPriority(functions.getPriority());
        savedFunctions.setNote(functions.getNote());
        functionsService.updateObj(savedFunctions);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/functions/function/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        functionsService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
