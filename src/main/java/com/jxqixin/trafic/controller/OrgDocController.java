package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgDoc;
import com.jxqixin.trafic.service.IOrgDocService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
/**
 * 企业资质文件控制器
 */
@RestController
public class OrgDocController extends CommonController{
    @Autowired
    private IOrgDocService orgDocService;
    /**
     * 查询所有企业资质文件
     * @return
     */
    @GetMapping("/orgDoc/orgDocs")
    public JsonResult<List<OrgDoc>> queryAllOrgDoc(){
        List<OrgDoc> list = orgDocService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询企业资质文件
     * @param nameDto
     * @return
     */
    @GetMapping("/orgDoc/orgDocsByPage")
    public ModelMap queryOrgDocs(NameDto nameDto){
        Page page = orgDocService.findOrgDocs(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增企业资质文件
     * @param orgDoc
     * @return
     */
    @PostMapping("/orgDoc/orgDoc")
    public JsonResult addOrgDoc(OrgDoc orgDoc){
        orgDoc.setId(UUID.randomUUID().toString());
        orgDocService.addObj(orgDoc);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑企业资质文件
     * @param orgDoc
     * @return
     */
    @PutMapping("/orgDoc/orgDoc")
    public JsonResult updateOrgDoc(OrgDoc orgDoc){
        OrgDoc savedOrgDoc = orgDocService.queryObjById(orgDoc.getId());
        savedOrgDoc.setName(orgDoc.getName());
        orgDocService.updateObj(savedOrgDoc);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除企业资质文件
     * @param id
     * @return
     */
    @DeleteMapping("/orgDoc/orgDoc/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            orgDocService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
