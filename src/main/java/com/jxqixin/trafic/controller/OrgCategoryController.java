package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.OrgCategoryDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.IOrgCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 企业类别控制器
 */
@RestController
public class OrgCategoryController extends CommonController{
    @Autowired
    private IOrgCategoryService orgCategoryService;
    /**
     * 查询所有企业类别
     * @return
     */
    @GetMapping("/orgCategory/orgCategorys")
    public JsonResult<List<OrgCategory>> queryAllOrgCategory(){
        List<OrgCategory> list = orgCategoryService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询企业类别
     * @param nameDto
     * @return
     */
    @GetMapping("/orgCategory/orgCategorysByPage")
    public ModelMap queryOrgCategorys(NameDto nameDto){
        Page page = orgCategoryService.findOrgCategorys(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增企业类别
     * @param orgCategory
     * @return
     */
    @PostMapping("/orgCategory/orgCategory")
    public JsonResult addOrgCategory(OrgCategory orgCategory){
        JsonResult jsonResult = findByName(orgCategory.getName());
        if(jsonResult.getResult().getResultCode()==200){
            orgCategory.setId(UUID.randomUUID().toString());
            orgCategory.setCreateDate(new Date());
            orgCategoryService.addObj(orgCategory);
        }
        return jsonResult;
    }
    /**
     * 编辑企业类别
     * @param orgCategoryDto
     * @return
     */
    @PutMapping("/orgCategory/orgCategory")
    public JsonResult updateOrgCategory(OrgCategoryDto orgCategoryDto){
        OrgCategory s = orgCategoryService.findByName(orgCategoryDto.getName());
        if(s!=null && !s.getId().equals(orgCategoryDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        OrgCategory savedOrgCategory = orgCategoryService.queryObjById(orgCategoryDto.getId());
        savedOrgCategory.setName(orgCategoryDto.getName());
        savedOrgCategory.setNote(orgCategoryDto.getNote());
        savedOrgCategory.setSafetyCostRatio(orgCategoryDto.getSafetyCostRatio());
        orgCategoryService.updateObj(savedOrgCategory);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据企业类别名称查找
     * @param name
     * @return
     */
    @GetMapping("/orgCategory/orgCategory/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        OrgCategory orgCategory = orgCategoryService.findByName(name);
        if(orgCategory==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除企业类别
     * @param id
     * @return
     */
    @DeleteMapping("/orgCategory/orgCategory/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            orgCategoryService.deleteById(id);
        }catch(RuntimeException re){
            Result result = Result.FAIL;
            result.setMessage(re.getMessage());
            JsonResult jsonResult = new JsonResult(result);
            return jsonResult;
        }
        return new JsonResult(Result.SUCCESS);
    }
}
