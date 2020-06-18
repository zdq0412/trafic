package com.jxqixin.trafic;

import com.jxqixin.trafic.model.Role;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IRoleService;
import com.jxqixin.trafic.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;

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
}
