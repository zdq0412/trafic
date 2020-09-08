package com.jxqixin.trafic;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.*;
import com.jxqixin.trafic.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import java.io.File;
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
    private ICategoryService categoryService;
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
    @Autowired
    private ISafetyInvestmentCategoryService safetyInvestmentCategoryService;
    @Autowired
    private IRiskLevelService riskLevelService;
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
        excelUtil.getFunctionsData(file);
    }
    /**
     * 为超级管理员角色赋予权限
     */
    @Test
    public void addRoleFunctions(){
        List<Functions> list = functionsService.findAdminRoleFunctions();
       // List<Functions> list = functionsService.findAll();
        Role role = roleService.findByNameAndOrgId("admin",null);

        list.forEach(function -> {
            RoleFunctions roleFunctions = new RoleFunctions();
            roleFunctions.setRole(role);
            roleFunctions.setFunctions(function);
           roleFunctionsService.addObj(roleFunctions);
        });
    }
    @Test
    public void importSafetyInvestmentCategory(){
        ExcelUtil excelUtil = new ImportSafetyInvestmentCategoryUtil();
        File file = new File("d:/safetyInvestmentCategory.xlsx");
        List<SafetyInvestmentCategory> list = excelUtil.getSafetyInvestmentCategoryData(file);
        list.forEach(safetyInvestmentCategory -> {
            safetyInvestmentCategoryService.addObj(safetyInvestmentCategory);
        });
    }
    /**
     * 初始化风险等级
     */
    @Test
    public void initRiskLevel(){
        RiskLevel rl0 = new RiskLevel();
        rl0.setColor("#FF0000");
        rl0.setUpperLimit(25);
        rl0.setLowerLimit(20);
        rl0.setName("重大风险");
        rl0.setTimeLimit("立刻");
        rl0.setMeasure("在采取措施降低风险前,不能继续作业,对改进措施进行评估");
        riskLevelService.addObj(rl0);

        RiskLevel rl1 = new RiskLevel();
        rl1.setColor("#E6A23C");
        rl1.setUpperLimit(16);
        rl1.setLowerLimit(15);
        rl1.setName("较大风险");
        rl1.setTimeLimit("立刻或近期整改");
        rl1.setMeasure("采取紧急措施降低风险,建立运行控制程序,定期检查、测量及评估");
        riskLevelService.addObj(rl1);

        RiskLevel rl2 = new RiskLevel();
        rl2.setColor("#ffff00");
        rl2.setUpperLimit(12);
        rl2.setLowerLimit(9);
        rl2.setName("一般风险");
        rl2.setTimeLimit("2年内治理");
        rl2.setMeasure("可考虑建立目标、建立操作规程,加强培训及沟通");
        riskLevelService.addObj(rl2);

        RiskLevel rl3 = new RiskLevel();
        rl3.setColor("#6699CC");
        rl3.setUpperLimit(8);
        rl3.setLowerLimit(1);
        rl3.setName("低风险");
        rl3.setTimeLimit("有条件、有经费时治理或只需保存记录");
        rl3.setMeasure("可考虑建立操作规程、作业指导书但需定期检查或无需采用控制措施");
        riskLevelService.addObj(rl3);
    }
    /**
     * 导入省市区
     */
    @Test
    public void importCategory(){
        ExcelUtil excelUtil = new ImportCategoryUtil();
        File file = new File("d:/category.xlsx");
        List<Category> list = excelUtil.getCategoryData(file);
        categoryService.importCategory(list);
    }
}
