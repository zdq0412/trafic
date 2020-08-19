package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.RiskCheckTemplateDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IRiskCheckTemplateService;
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
 * 风险排查模板控制器
 */
@RestController
public class RiskCheckTemplateController extends CommonController{
    @Autowired
    private IRiskCheckTemplateService riskCheckTemplateService;
    @Autowired
    private IUserService userService;
    /**
     * 分页查询
     * @param riskCheckTemplateDto
     * @return
     */
    @GetMapping("/riskCheckTemplate/riskCheckTemplatesByPage")
    public ModelMap queryRiskCheckTemplates(RiskCheckTemplateDto riskCheckTemplateDto,HttpServletRequest request){
        Page page = riskCheckTemplateService.findRiskCheckTemplates(riskCheckTemplateDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param riskCheckTemplateDto
     * @return
     */
    @PostMapping("/riskCheckTemplate/riskCheckTemplate")
    public JsonResult addRiskCheckTemplate(RiskCheckTemplateDto riskCheckTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        RiskCheckTemplate savedRiskCheckTemplate = new RiskCheckTemplate();
        BeanUtils.copyProperties(riskCheckTemplateDto,savedRiskCheckTemplate);
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedRiskCheckTemplate.setUrl(urlMapping);
            savedRiskCheckTemplate.setRealPath(savedFile.getAbsolutePath());
            savedRiskCheckTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(riskCheckTemplateDto.getProvinceId());

            savedRiskCheckTemplate.setProvince(province);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(riskCheckTemplateDto.getCityId());

            savedRiskCheckTemplate.setCity(city);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(riskCheckTemplateDto.getRegionId());

            savedRiskCheckTemplate.setRegion(region);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(riskCheckTemplateDto.getOrgCategoryId());

            savedRiskCheckTemplate.setOrgCategory(orgCategory);
        }
        savedRiskCheckTemplate.setCreateDate(new Date());
        savedRiskCheckTemplate.setCreator(getCurrentUsername(request));
        savedRiskCheckTemplate.setOrg(getOrg(request));
        riskCheckTemplateService.addObj(savedRiskCheckTemplate);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param riskCheckTemplateDto
     * @return
     */
    @PostMapping("/riskCheckTemplate/updateRiskCheckTemplate")
    public JsonResult updateRiskCheckTemplate(RiskCheckTemplateDto riskCheckTemplateDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        RiskCheckTemplate savedRiskCheckTemplate = riskCheckTemplateService.queryObjById(riskCheckTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "template/emp";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedRiskCheckTemplate.setUrl(urlMapping);
            if(!StringUtils.isEmpty(savedRiskCheckTemplate.getRealPath())){
                FileUtil.deleteFile(savedRiskCheckTemplate.getRealPath());
            }
            savedRiskCheckTemplate.setRealPath(savedFile.getAbsolutePath());
            savedRiskCheckTemplate.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(riskCheckTemplateDto.getProvinceId());

            savedRiskCheckTemplate.setProvince(province);
        }else{
            savedRiskCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(riskCheckTemplateDto.getCityId());

            savedRiskCheckTemplate.setCity(city);
        }else{
            savedRiskCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(riskCheckTemplateDto.getRegionId());

            savedRiskCheckTemplate.setRegion(region);
        }else{
            savedRiskCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(riskCheckTemplateDto.getOrgCategoryId());

            savedRiskCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedRiskCheckTemplate.setOrgCategory(null);
        }
        savedRiskCheckTemplate.setName(riskCheckTemplateDto.getName());
        savedRiskCheckTemplate.setNote(riskCheckTemplateDto.getNote());
        riskCheckTemplateService.updateObj(savedRiskCheckTemplate);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param riskCheckTemplateDto
     * @return
     */
    @PostMapping("/riskCheckTemplate/updateRiskCheckTemplateNoFile")
    public JsonResult updateRiskCheckTemplateNoFile(RiskCheckTemplateDto riskCheckTemplateDto,HttpServletRequest request){
        RiskCheckTemplate savedRiskCheckTemplate = riskCheckTemplateService.queryObjById(riskCheckTemplateDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getProvinceId())){
            Category province = new Category();
            province.setId(riskCheckTemplateDto.getProvinceId());

            savedRiskCheckTemplate.setProvince(province);
        }else{
            savedRiskCheckTemplate.setProvince(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getCityId())){
            Category city = new Category();
            city.setId(riskCheckTemplateDto.getCityId());

            savedRiskCheckTemplate.setCity(city);
        }else{
            savedRiskCheckTemplate.setCity(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getRegionId())){
            Category region = new Category();
            region.setId(riskCheckTemplateDto.getRegionId());

            savedRiskCheckTemplate.setRegion(region);
        }else{
            savedRiskCheckTemplate.setRegion(null);
        }
        if(!StringUtils.isEmpty(riskCheckTemplateDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(riskCheckTemplateDto.getOrgCategoryId());

            savedRiskCheckTemplate.setOrgCategory(orgCategory);
        }else {
            savedRiskCheckTemplate.setOrgCategory(null);
        }
        savedRiskCheckTemplate.setName(riskCheckTemplateDto.getName());
        savedRiskCheckTemplate.setNote(riskCheckTemplateDto.getNote());
        riskCheckTemplateService.updateObj(savedRiskCheckTemplate);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/riskCheckTemplate/riskCheckTemplate/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        riskCheckTemplateService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
