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
