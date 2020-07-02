package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.AreaManager;
import com.jxqixin.trafic.service.IAreaManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 区域管理员控制器
 */
@RestController
public class AreaManagerController extends CommonController{
    @Autowired
    private IAreaManagerService areaManagerService;
    /**
     * 查询所有区域管理员
     * @return
     */
    @GetMapping("/areaManager/areaManagers")
    public JsonResult<List<AreaManager>> queryAllAreaManager(){
        List<AreaManager> list = areaManagerService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询区域管理员
     * @param nameDto
     * @return
     */
    @GetMapping("/areaManager/areaManagersByPage")
    public ModelMap queryAreaManagers(NameDto nameDto){
        Page page = areaManagerService.findAreaManagers(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增区域管理员
     * @param areaManager
     * @return
     */
    @PostMapping("/areaManager/areaManager")
    public JsonResult addAreaManager(AreaManager areaManager){
        JsonResult jsonResult = findByName(areaManager.getUsername());
        if(jsonResult.getResult().getResultCode()==200){
            areaManager.setId(UUID.randomUUID().toString());
            areaManager.setCreateDate(new Date());
            areaManagerService.addObj(areaManager);
        }
        return jsonResult;
    }
    /**
     * 编辑区域管理员
     * @param areaManager
     * @return
     */
    @PutMapping("/areaManager/areaManager")
    public JsonResult updateAreaManager(AreaManager areaManager){
        AreaManager s = areaManagerService.findByUsername(areaManager.getUsername());

        if(s!=null && !s.getId().equals(areaManager.getId())){
            return new JsonResult(Result.FAIL);
        }
        AreaManager savedAreaManager = areaManagerService.queryObjById(areaManager.getId());
        areaManager.setCreateDate(savedAreaManager.getCreateDate());
        String privence = areaManager.getProvince();
        areaManagerService.updateObj(areaManager);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据区域管理员名称查找
     * @param name
     * @return
     */
    @GetMapping("/areaManager/areaManager/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        AreaManager areaManager = areaManagerService.findByUsername(name);
        if(areaManager==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除区域管理员
     * @param id
     * @return
     */
    @DeleteMapping("/areaManager/areaManager/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        areaManagerService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
