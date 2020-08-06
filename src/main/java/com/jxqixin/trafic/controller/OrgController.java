package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmployeeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.OrgDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IOrgDocService;
import com.jxqixin.trafic.service.IOrgImgService;
import com.jxqixin.trafic.service.IOrgService;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 企业控制器
 */
@RestController
public class OrgController extends CommonController{
    @Autowired
    private IOrgService orgService;
    @Autowired
    private IOrgImgService orgImgService;
    @Autowired
    private IOrgDocService orgDocService;
    @Autowired
    private IUserService userService;
    /**
     * 查询所有企业
     * @return
     */
    @GetMapping("/org/orgs")
    public JsonResult<List<Org>> queryAllOrg(){
        List<Org> list = orgService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询企业
     * @param nameDto
     * @return
     */
    @GetMapping("/org/orgsByPage")
    public ModelMap queryOrgs(NameDto nameDto){
        Page page = orgService.findOrgs(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增企业
     * @param orgDto
     * @return
     */
    @PostMapping("/org/org")
    public JsonResult addOrg(OrgDto orgDto){
        JsonResult jsonResult = checkCodeAndName(orgDto.getName(),orgDto.getCode());
        if(jsonResult.getResult().getResultCode()==200){
            Org org = new Org();
            BeanUtils.copyProperties(orgDto,org);
            org.setId(UUID.randomUUID().toString());
            org.setCreateDate(new Date());
            org.setReportTel(orgDto.getTel());
            if(!StringUtils.isEmpty(orgDto.getOrgCategoryId())){
                OrgCategory orgCategory = new OrgCategory();
                orgCategory.setId(orgDto.getOrgCategoryId());
                org.setOrgCategory(orgCategory);
            }
            if(!StringUtils.isEmpty(orgDto.getProvinceId())){
                Category province = new Category();
                province.setId(orgDto.getProvinceId());
                org.setProvince(province);
            }
            if(!StringUtils.isEmpty(orgDto.getCityId())){
                Category city = new Category();
                city.setId(orgDto.getCityId());
                org.setCity(city);
            }
            if(!StringUtils.isEmpty(orgDto.getRegionId())){
                Category region = new Category();
                region.setId(orgDto.getRegionId());
                org.setRegion(region);
            }
            try {
                orgService.addOrg(org);
            }catch (RuntimeException e){
                Result result = Result.FAIL;
                result.setMessage(e.getMessage());
                return new JsonResult(result);
            }
        }
        return jsonResult;
    }
    /**
     * 编辑企业
     * @param orgDto
     * @return
     */
    @PostMapping("/org/updateOrg")
    public JsonResult updateOrg(OrgDto orgDto){
        Org s = orgService.findByName(orgDto.getName());

        Result fail = Result.FAIL;
        if(s!=null && !s.getId().equals(orgDto.getId())){
            fail.setMessage("企业名称已被使用!");
            return new JsonResult(fail);
        }
        s = orgService.findByCode(orgDto.getCode());
        if(s!=null && !s.getId().equals(orgDto.getId())){
            fail.setMessage("企业代码已被使用!");
            return new JsonResult(fail);
        }
        Org savedOrg = orgService.queryObjById(orgDto.getId());
        savedOrg.setCode(orgDto.getCode());
        savedOrg.setName(orgDto.getName());
        savedOrg.setContact(orgDto.getContact());
        savedOrg.setAddr(orgDto.getAddr());
        savedOrg.setTel(orgDto.getTel());
        savedOrg.setLegalPerson(orgDto.getLegalPerson());
        savedOrg.setNote(orgDto.getNote());
        if(!StringUtils.isEmpty(orgDto.getProvinceId())){
            Category province = new Category();
            province.setId(orgDto.getProvinceId());
            savedOrg.setProvince(province);
        }
        if(!StringUtils.isEmpty(orgDto.getCityId())){
            Category city = new Category();
            city.setId(orgDto.getCityId());
            savedOrg.setCity(city);
        }
        if(!StringUtils.isEmpty(orgDto.getRegionId())){
            Category region = new Category();
            region.setId(orgDto.getRegionId());
            savedOrg.setRegion(region);
        }
        savedOrg.setReportTel(orgDto.getReportTel());
        savedOrg.setShortName(orgDto.getShortName());
        savedOrg.setEmail(orgDto.getEmail());
        // savedOrg.setEstablishedTime(orgDto.getEstablishedTime());
        savedOrg.setBusinessScope(orgDto.getBusinessScope());
        savedOrg.setIntroduction(orgDto.getIntroduction());
        if(!StringUtils.isEmpty(orgDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(orgDto.getOrgCategoryId());

            savedOrg.setOrgCategory(orgCategory);
        }
        orgService.updateObj(savedOrg);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改企业介绍
     * @param orgDto
     * @return
     */
    @PostMapping("/org/orgIntroduction")
    public JsonResult orgIntroduction(OrgDto orgDto){
        Org savedOrg = orgService.queryObjById(orgDto.getId());
        savedOrg.setIntroduction(orgDto.getIntroduction());
        orgService.updateObj(savedOrg);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 根据企业名称和代码查找企业信息
     * @param name
     * @param code
     * @return
     */
    public JsonResult checkCodeAndName(String name,String code){
        Org org = orgService.findByName(name);
        Result fail = Result.FAIL;
        if(org==null){
           /* org = orgService.findByCode(code);
            if(org==null) {*/
            return new JsonResult(Result.SUCCESS);
           /* }else{
                fail.setMessage("企业代码已被使用!");
                return new JsonResult(fail);
            }*/
        }else{
            fail.setMessage("企业名称已被使用!");
            return new JsonResult(fail);
        }
    }
    /**
     * 根据ID删除企业
     * @param id
     * @return
     */
    @DeleteMapping("/org/org/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        orgService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 查询企业对象，企业图片，企业资质文件
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/org/orgInfo")
    public ModelMap findOrgInfo(HttpServletRequest httpServletRequest){
        User user = userService.queryUserByUsername(getCurrentUsername(httpServletRequest));
        Org org = user.getOrg();
        List<OrgImg> orgImgList = orgImgService.findAll(org);
        List<OrgDoc> orgDocList = orgDocService.findAll(org);

        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("org",org);
        modelMap.addAttribute("orgImgList",orgImgList);
        modelMap.addAttribute("orgDocList",orgDocList);
        return modelMap;
    }
    /**
     * 查找企业四色图
     * @param httpServletRequest
     * @return
     */
    @GetMapping("/org/fourColorPic")
    public JsonResult fourColorPic(HttpServletRequest httpServletRequest){
        User user = userService.queryUserByUsername(getCurrentUsername(httpServletRequest));
        Org org = user.getOrg();
        if(org == null){
            return new JsonResult<>(Result.FAIL);
        }
        return new JsonResult(Result.SUCCESS,orgService.findFourColorPic(org.getId()));
    }

    /**
     * 上传四色图
     * @return
     */
    @PostMapping("/org/upload4ColorPic")
    public JsonResult upload4ColorPic(@RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            Org org = user.getOrg();
            if(org!=null) {
                String dir = (user.getOrg() == null ? (user.getUsername() + "/") : (user.getOrg().getName() + "/")) + "fourColor";
                File savedFile = upload(dir, file);
                if (savedFile != null) {
                    urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
                }
                if(!StringUtils.isEmpty(org.getFourColorPicUrl())){
                    if(!org.getFourColorPicUrl().equals(urlMapping)){
                        File oldPic = new File(org.getFourColorPicRealPath());
                        oldPic.delete();
                    }
                }
                org.setFourColorPicUrl(urlMapping);
                org.setFourColorPicRealPath(savedFile.getAbsolutePath());
                orgService.updateObj(org);
            }else{
                result = Result.FAIL;
                result.setMessage("当前登录用户非企业内部用户，不能执行该操作!");
                return new JsonResult(result);
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urlMapping);
    }
}
