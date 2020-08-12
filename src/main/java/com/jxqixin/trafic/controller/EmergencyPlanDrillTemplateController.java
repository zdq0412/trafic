package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmergencyPlanDrillTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IEmergencyPlanDrillTemplateService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 应急预案演练模板控制器
 */
@RestController
public class EmergencyPlanDrillTemplateController extends CommonController{
    @Autowired
    private IEmergencyPlanDrillTemplateService emergencyPlanDrillTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param emergencyPlanDrillTemplateDto
     * @return
     */
    @GetMapping("/emergencyPlanDrillTemplate/emergencyPlanDrillTemplatesByPage")
    public ModelMap queryEmergencyPlanDrillTemplates(EmergencyPlanDrillTemplateDto emergencyPlanDrillTemplateDto,HttpServletRequest request){
        Page page = emergencyPlanDrillTemplateService.findEmergencyPlanDrillTemplates(emergencyPlanDrillTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param emergencyPlanDrillTemplateDto
     * @return
     */
    @PostMapping("/emergencyPlanDrillTemplate/emergencyPlanDrillTemplate")
    public JsonResult addEmergencyPlanDrillTemplate(EmergencyPlanDrillTemplateDto emergencyPlanDrillTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        EmergencyPlanDrillTemplate savedEmergencyPlanDrillTemplate = new EmergencyPlanDrillTemplate();
        BeanUtils.copyProperties(emergencyPlanDrillTemplateDto,savedEmergencyPlanDrillTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmergencyPlanDrillTemplate.setUrl(urlMapping);
            savedEmergencyPlanDrillTemplate.setRealPath(savedFile.getAbsolutePath());
            savedEmergencyPlanDrillTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(emergencyPlanDrillTemplateDto.getProvinceId());

            savedEmergencyPlanDrillTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(emergencyPlanDrillTemplateDto.getCityId());

            savedEmergencyPlanDrillTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(emergencyPlanDrillTemplateDto.getRegionId());

            savedEmergencyPlanDrillTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(emergencyPlanDrillTemplateDto.getOrgCategoryId());

            savedEmergencyPlanDrillTemplate.setOrgCategory(orgCategory);
        }
        savedEmergencyPlanDrillTemplate.setCreateDate(new Date());
        savedEmergencyPlanDrillTemplate.setCreator(getCurrentUsername(request));
        savedEmergencyPlanDrillTemplate.setOrg(getOrg(request));
        emergencyPlanDrillTemplateService.addObj(savedEmergencyPlanDrillTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param emergencyPlanDrillTemplateDto
     * @return
     */
    @PutMapping("/emergencyPlanDrillTemplate/emergencyPlanDrillTemplate")
    public JsonResult updateEmergencyPlanDrillTemplate(EmergencyPlanDrillTemplateDto emergencyPlanDrillTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        EmergencyPlanDrillTemplate savedEmergencyPlanDrillTemplate = emergencyPlanDrillTemplateService.queryObjById(emergencyPlanDrillTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmergencyPlanDrillTemplate.setUrl(urlMapping);
            savedEmergencyPlanDrillTemplate.setRealPath(savedFile.getAbsolutePath());
            savedEmergencyPlanDrillTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(emergencyPlanDrillTemplateDto.getProvinceId());

            savedEmergencyPlanDrillTemplate.setProvince(province);
        }else{
            savedEmergencyPlanDrillTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(emergencyPlanDrillTemplateDto.getCityId());

            savedEmergencyPlanDrillTemplate.setCity(city);
        }else{
            savedEmergencyPlanDrillTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(emergencyPlanDrillTemplateDto.getRegionId());

            savedEmergencyPlanDrillTemplate.setRegion(region);
        }else{
            savedEmergencyPlanDrillTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(emergencyPlanDrillTemplateDto.getOrgCategoryId());

            savedEmergencyPlanDrillTemplate.setOrgCategory(orgCategory);
        }else {
            savedEmergencyPlanDrillTemplate.setOrgCategory(null);
        }
        /*if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getBeginDate())){
            try {
                savedEmergencyPlanDrillTemplate.setBeginDate(format.parse(emergencyPlanDrillTemplateDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrillTemplate.setBeginDate(null);
            }
        }else{
            savedEmergencyPlanDrillTemplate.setBeginDate(null);
        }
        if(!StringUtils.isEmpty(emergencyPlanDrillTemplateDto.getEndDate())){
            try {
                savedEmergencyPlanDrillTemplate.setEndDate(format.parse(emergencyPlanDrillTemplateDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedEmergencyPlanDrillTemplate.setEndDate(null);
            }
        }else{
            savedEmergencyPlanDrillTemplate.setEndDate(null);
        }*/
        savedEmergencyPlanDrillTemplate.setName(emergencyPlanDrillTemplateDto.getName());
        savedEmergencyPlanDrillTemplate.setNote(emergencyPlanDrillTemplateDto.getNote());
        emergencyPlanDrillTemplateService.updateObj(savedEmergencyPlanDrillTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/emergencyPlanDrillTemplate/emergencyPlanDrillTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        emergencyPlanDrillTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
