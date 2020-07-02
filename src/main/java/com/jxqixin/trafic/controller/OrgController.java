package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.service.IOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
     * @param org
     * @return
     */
    @PostMapping("/org/org")
    public JsonResult addOrg(Org org){
        JsonResult jsonResult = findByName(org.getName());
        if(jsonResult.getResult().getResultCode()==200){
            org.setId(UUID.randomUUID().toString());
            org.setCreateDate(new Date());
            orgService.addObj(org);
        }
        return jsonResult;
    }
    /**
     * 编辑企业
     * @param org
     * @return
     */
    @PutMapping("/org/org")
    public JsonResult updateOrg(Org org){
        Org s = orgService.findByName(org.getName());

        if(s!=null && !s.getId().equals(org.getId())){
            return new JsonResult(Result.FAIL);
        }
        Org savedOrg = orgService.queryObjById(org.getId());
        org.setCreateDate(savedOrg.getCreateDate());
        orgService.updateObj(org);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据企业名称查找
     * @param name
     * @return
     */
    @GetMapping("/org/org/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Org org = orgService.findByName(name);
        if(org==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
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
