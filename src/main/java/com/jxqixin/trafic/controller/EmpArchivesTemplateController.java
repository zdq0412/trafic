package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmpArchivesTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IEmpArchivesTemplateService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
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
 * 人员档案模板控制器
 */
@RestController
public class EmpArchivesTemplateController extends CommonController{
    @Autowired
    private IEmpArchivesTemplateService empArchivesTemplateService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param empArchivesTemplateDto
     * @return
     */
    @GetMapping("/empArchivesTemplate/empArchivesTemplatesByPage")
    public ModelMap queryEmpArchivesTemplates(EmpArchivesTemplateDto empArchivesTemplateDto,HttpServletRequest request){
        Page page = empArchivesTemplateService.findEmpArchivesTemplates(empArchivesTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param empArchivesTemplateDto
     * @return
     */
    @PostMapping("/empArchivesTemplate/empArchivesTemplate")
    public JsonResult addEmpArchivesTemplate(EmpArchivesTemplateDto empArchivesTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        EmpArchivesTemplate savedEmpArchivesTemplate = new EmpArchivesTemplate();
        BeanUtils.copyProperties(empArchivesTemplateDto,savedEmpArchivesTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmpArchivesTemplate.setUrl(urlMapping);
            savedEmpArchivesTemplate.setRealPath(savedFile.getAbsolutePath());
            savedEmpArchivesTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(empArchivesTemplateDto.getProvinceId());

            savedEmpArchivesTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(empArchivesTemplateDto.getCityId());

            savedEmpArchivesTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(empArchivesTemplateDto.getRegionId());

            savedEmpArchivesTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(empArchivesTemplateDto.getOrgCategoryId());

            savedEmpArchivesTemplate.setOrgCategory(orgCategory);
        }
        savedEmpArchivesTemplate.setCreateDate(new Date());
        savedEmpArchivesTemplate.setCreator(getCurrentUsername(request));
        savedEmpArchivesTemplate.setOrg(getOrg(request));
        empArchivesTemplateService.addObj(savedEmpArchivesTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param empArchivesTemplateDto
     * @return
     */
    @PostMapping("/empArchivesTemplate/updateEmpArchivesTemplate")
    public JsonResult updateEmpArchivesTemplate(EmpArchivesTemplateDto empArchivesTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        EmpArchivesTemplate savedEmpArchivesTemplate = empArchivesTemplateService.queryObjById(empArchivesTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedEmpArchivesTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedEmpArchivesTemplate.getRealPath())){
                FileUtil.deleteFile(savedEmpArchivesTemplate.getRealPath());
            }
            savedEmpArchivesTemplate.setRealPath(savedFile.getAbsolutePath());
            savedEmpArchivesTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(empArchivesTemplateDto.getProvinceId());

            savedEmpArchivesTemplate.setProvince(province);
        }else{
            savedEmpArchivesTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(empArchivesTemplateDto.getCityId());

            savedEmpArchivesTemplate.setCity(city);
        }else{
            savedEmpArchivesTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(empArchivesTemplateDto.getRegionId());

            savedEmpArchivesTemplate.setRegion(region);
        }else{
            savedEmpArchivesTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(empArchivesTemplateDto.getOrgCategoryId());

            savedEmpArchivesTemplate.setOrgCategory(orgCategory);
        }else {
            savedEmpArchivesTemplate.setOrgCategory(null);
        }
        savedEmpArchivesTemplate.setType(empArchivesTemplateDto.getType());
        savedEmpArchivesTemplate.setName(empArchivesTemplateDto.getName());
        savedEmpArchivesTemplate.setNote(empArchivesTemplateDto.getNote());
        empArchivesTemplateService.updateObj(savedEmpArchivesTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param empArchivesTemplateDto
     * @return
     */
    @PostMapping("/empArchivesTemplate/updateEmpArchivesTemplateNoFile")
    public JsonResult updateEmpArchivesTemplateNoFile(EmpArchivesTemplateDto empArchivesTemplateDto,HttpServletRequest request){
        EmpArchivesTemplate savedEmpArchivesTemplate = empArchivesTemplateService.queryObjById(empArchivesTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(empArchivesTemplateDto.getProvinceId());

            savedEmpArchivesTemplate.setProvince(province);
        }else{
            savedEmpArchivesTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(empArchivesTemplateDto.getCityId());

            savedEmpArchivesTemplate.setCity(city);
        }else{
            savedEmpArchivesTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(empArchivesTemplateDto.getRegionId());

            savedEmpArchivesTemplate.setRegion(region);
        }else{
            savedEmpArchivesTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(empArchivesTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(empArchivesTemplateDto.getOrgCategoryId());

            savedEmpArchivesTemplate.setOrgCategory(orgCategory);
        }else {
            savedEmpArchivesTemplate.setOrgCategory(null);
        }
        savedEmpArchivesTemplate.setType(empArchivesTemplateDto.getType());
        savedEmpArchivesTemplate.setName(empArchivesTemplateDto.getName());
        savedEmpArchivesTemplate.setNote(empArchivesTemplateDto.getNote());
        empArchivesTemplateService.updateObj(savedEmpArchivesTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/empArchivesTemplate/empArchivesTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        empArchivesTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
