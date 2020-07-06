package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.OrgDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.IOrgService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

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
            if(!StringUtils.isEmpty(orgDto.getOrgCategoryId())){
                OrgCategory orgCategory = new OrgCategory();
                orgCategory.setId(orgDto.getOrgCategoryId());

                org.setOrgCategory(orgCategory);
            }
            orgService.addOrg(org);
        }
        return jsonResult;
    }
    /**
     * 编辑企业
     * @param orgDto
     * @return
     */
    @PutMapping("/org/org")
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
        savedOrg.setProvince(orgDto.getProvince());
        savedOrg.setCity(orgDto.getCity());
        savedOrg.setRegion(orgDto.getRegion());
        if(!StringUtils.isEmpty(orgDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(orgDto.getOrgCategoryId());

            savedOrg.setOrgCategory(orgCategory);
        }
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
            org = orgService.findByCode(code);
            if(org==null) {
                return new JsonResult(Result.SUCCESS);
            }else{
                fail.setMessage("企业代码已被使用!");
                return new JsonResult(fail);
            }
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
}
