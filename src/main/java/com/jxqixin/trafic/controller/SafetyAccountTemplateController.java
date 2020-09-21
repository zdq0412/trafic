package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.SafetyAccountTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.ISafetyAccountTemplateService;
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
import java.util.Date;
/**
 * 安全投入台账模板控制器
 */
@RestController
public class SafetyAccountTemplateController extends CommonController{
    @Autowired
    private ISafetyAccountTemplateService safetyAccountTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param safetyAccountTemplateDto
     * @return
     */
    @GetMapping("/safetyAccountTemplate/safetyAccountTemplatesByPage")
    public ModelMap querySafetyAccountTemplates(SafetyAccountTemplateDto safetyAccountTemplateDto,HttpServletRequest request){
        Page page = safetyAccountTemplateService.findSafetyAccountTemplates(safetyAccountTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param safetyAccountTemplateDto
     * @return
     */
    @PostMapping("/safetyAccountTemplate/safetyAccountTemplate")
    public JsonResult addSafetyAccountTemplate(SafetyAccountTemplateDto safetyAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SafetyAccountTemplate savedSafetyAccountTemplate = new SafetyAccountTemplate();
        BeanUtils.copyProperties(safetyAccountTemplateDto,savedSafetyAccountTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSafetyAccountTemplate.setUrl(urlMapping);
            savedSafetyAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSafetyAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(safetyAccountTemplateDto.getProvinceId());

            savedSafetyAccountTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(safetyAccountTemplateDto.getCityId());

            savedSafetyAccountTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(safetyAccountTemplateDto.getRegionId());

            savedSafetyAccountTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(safetyAccountTemplateDto.getOrgCategoryId());

            savedSafetyAccountTemplate.setOrgCategory(orgCategory);
        }
        savedSafetyAccountTemplate.setOrg(getOrg(request));
        savedSafetyAccountTemplate.setCreateDate(new Date());
        savedSafetyAccountTemplate.setCreator(getCurrentUsername(request));
        safetyAccountTemplateService.addObj(savedSafetyAccountTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param safetyAccountTemplateDto
     * @return
     */
    @PostMapping("/safetyAccountTemplate/updateSafetyAccountTemplate")
    public JsonResult updateSafetyAccountTemplate(SafetyAccountTemplateDto safetyAccountTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        SafetyAccountTemplate savedSafetyAccountTemplate = safetyAccountTemplateService.queryObjById(safetyAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "annualAccount";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedSafetyAccountTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedSafetyAccountTemplate.getRealPath())){
                FileUtil.deleteFile(savedSafetyAccountTemplate.getRealPath());
            }
            savedSafetyAccountTemplate.setRealPath(savedFile.getAbsolutePath());
            savedSafetyAccountTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(safetyAccountTemplateDto.getProvinceId());

            savedSafetyAccountTemplate.setProvince(province);
        }else{
            savedSafetyAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(safetyAccountTemplateDto.getCityId());

            savedSafetyAccountTemplate.setCity(city);
        }else{
            savedSafetyAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(safetyAccountTemplateDto.getRegionId());

            savedSafetyAccountTemplate.setRegion(region);
        }else{
            savedSafetyAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(safetyAccountTemplateDto.getOrgCategoryId());

            savedSafetyAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedSafetyAccountTemplate.setOrgCategory(null);
        }
        savedSafetyAccountTemplate.setName(safetyAccountTemplateDto.getName());
        savedSafetyAccountTemplate.setNote(safetyAccountTemplateDto.getNote());
        safetyAccountTemplateService.updateObj(savedSafetyAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param safetyAccountTemplateDto
     * @return
     */
    @PostMapping("/safetyAccountTemplate/updateSafetyAccountTemplateNoFile")
    public JsonResult updateSafetyAccountTemplateNoFile(SafetyAccountTemplateDto safetyAccountTemplateDto,HttpServletRequest request){
        SafetyAccountTemplate savedSafetyAccountTemplate = safetyAccountTemplateService.queryObjById(safetyAccountTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(safetyAccountTemplateDto.getProvinceId());
            savedSafetyAccountTemplate.setProvince(province);
        }else{
            savedSafetyAccountTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(safetyAccountTemplateDto.getCityId());

            savedSafetyAccountTemplate.setCity(city);
        }else{
            savedSafetyAccountTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(safetyAccountTemplateDto.getRegionId());

            savedSafetyAccountTemplate.setRegion(region);
        }else{
            savedSafetyAccountTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(safetyAccountTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(safetyAccountTemplateDto.getOrgCategoryId());

            savedSafetyAccountTemplate.setOrgCategory(orgCategory);
        }else {
            savedSafetyAccountTemplate.setOrgCategory(null);
        }
        savedSafetyAccountTemplate.setName(safetyAccountTemplateDto.getName());
        savedSafetyAccountTemplate.setNote(safetyAccountTemplateDto.getNote());
        safetyAccountTemplateService.updateObj(savedSafetyAccountTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/safetyAccountTemplate/safetyAccountTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        safetyAccountTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
