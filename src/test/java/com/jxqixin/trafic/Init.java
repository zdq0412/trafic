package com.jxqixin.trafic;

import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.*;
import com.jxqixin.trafic.util.ExcelUtil;
import com.jxqixin.trafic.util.ImportDirectoryUtil;
import com.jxqixin.trafic.util.ImportFunctionUtil;
import com.jxqixin.trafic.util.ImportSchemaUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 初始化数据测试类
 */
@SpringBootTest(classes = TraficServerApplication.class)
@RunWith(SpringRunner.class)
@WebAppConfiguration
public class Init{
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private ISchemaService schemaService;
    @Autowired
    private IDirectoryService directoryService;
    @Autowired
    private IFunctionsService functionsService;
    @Autowired
    private IDirectoryFunctionsService directoryFunctionsService;
    @Autowired
    private IRoleFunctionsService roleFunctionsService;
    @Test
    public void initAdminRoleAndUser(){
        Role adminRole = new Role();
        adminRole.setAllowedDelete(false);
        adminRole.setCreateDate(new Date());
        adminRole.setNote("超级管理员角色!");
        adminRole.setName("admin");
        adminRole.setStatus("0");

        roleService.addObj(adminRole);

        User admin = new User();
        admin.setUsername("admin");
        String password = new BCryptPasswordEncoder().encode("admin");
        admin.setPassword(password);
        admin.setAllowedDelete(false);
        admin.setNote("超级管理员用户!");
        admin.setCreateDate(new Date());
        admin.setRole(adminRole);

        userService.addObj(admin);
    }
    /**
     * 导入模式信息
     */
    @Test
    public void importSchema() throws IOException {
        ExcelUtil excelUtil = new ImportSchemaUtil();
        File file = new File("d:/schema.xlsx");
        List<Schema> list = excelUtil.getSchemaData(file);
        list.forEach(schema ->schemaService.addObj(schema));
    }
    /**
     * 导入目录信息
     */
    @Test
    public void importDirectory(){
        ExcelUtil excelUtil = new ImportDirectoryUtil(schemaService);
        File file = new File("d:/directory.xlsx");
        List<Directory> list = excelUtil.getDirectoryData(file);
        list.forEach(directory ->directoryService.addObj(directory));
    }
    /**
     * 导入菜单(权限)
     */
    @Test
    public void importFunctions(){
        ExcelUtil excelUtil = new ImportFunctionUtil(functionsService,directoryService,directoryFunctionsService);
        File file = new File("d:/function.xlsx");
        List<Functions> list = excelUtil.getFunctionsData(file);
      //  list.forEach(function ->functionsService.addObj(function));
    }
  /*  *//**
     * 添加目录与权限关系
     *//*
    @Test
    public void addDirectoryFunctions(){
        List<Functions> list = functionsService.findAll();
        Directory directory = directoryService.queryObjById("1");

        list.forEach(function -> {
            DirectoryFunctions directoryFunctions = new DirectoryFunctions();
            directoryFunctions.setDirectory(directory);
            directoryFunctions.setFunctions(function);

            directoryFunctionsService.addObj(directoryFunctions);
        });
    }*/
    /**
     * 为超级管理员角色赋予权限
     */
    @Test
    public void addRoleFunctions(){
        List<Functions> list = functionsService.findAll();
        Role role = roleService.queryRoleByRolename("admin");

        list.forEach(function -> {
            RoleFunctions roleFunctions = new RoleFunctions();
            roleFunctions.setRole(role);
            roleFunctions.setFunctions(function);

           roleFunctionsService.addObj(roleFunctions);
        });
    }
}
