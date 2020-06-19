package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.IDirectoryService;
import com.jxqixin.trafic.service.IFunctionsService;
import com.jxqixin.trafic.service.ISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 权限控制器
 */
@RestController
@RequestMapping("functions")
public class FunctionController extends CommonController{
    @Autowired
    private IFunctionsService functionsService;
    @Autowired
    private IDirectoryService directoryService;
    /**
     * 查询用户显示菜单
     * @param username
     * @return
     */
    @GetMapping("/menus")
    public JsonResult<Map<Directory, Functions>> queryMenus(String username){
            Map<Directory,Functions> map = new HashMap<>();
            //查询目录
            List<Directory> directories =  directoryService.findDirectories();
        return new JsonResult<>(true,map);
    }
}
