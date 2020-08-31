package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.AreaManagerDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IAreaManagerService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
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
    @Autowired
    private IUserService userService;
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
     * @param areaManagerDto
     * @return
     */
    @PostMapping("/areaManager/areaManager")
    public JsonResult addAreaManager(AreaManagerDto areaManagerDto){
        JsonResult jsonResult = findByName(areaManagerDto.getUsername());
        AreaManager areaManager = new AreaManager();
        BeanUtils.copyProperties(areaManagerDto,areaManager);
        if(jsonResult.getResult().getResultCode()==200){
            areaManager.setId(UUID.randomUUID().toString());
            areaManager.setCreateDate(new Date());
            if(!StringUtils.isEmpty(areaManagerDto.getOrgCategoryId())){
                OrgCategory orgCategory = new OrgCategory();
                orgCategory.setId(areaManagerDto.getOrgCategoryId());
                areaManager.setOrgCategory(orgCategory);
            }
            areaManagerService.addObj(areaManager);
        }
        return jsonResult;
    }
    /**
     * 编辑区域管理员
     * @param areaManagerDto
     * @return
     */
    @PutMapping("/areaManager/areaManager")
    public JsonResult updateAreaManager(AreaManagerDto areaManagerDto){
        AreaManager s = areaManagerService.findByUsername(areaManagerDto.getUsername());

        if(s!=null && !s.getId().equals(areaManagerDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        AreaManager savedAreaManager = areaManagerService.queryObjById(areaManagerDto.getId());
        if(!StringUtils.isEmpty(areaManagerDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(areaManagerDto.getOrgCategoryId());
            savedAreaManager.setOrgCategory(orgCategory);
        }
        savedAreaManager.setUsername(areaManagerDto.getUsername());
        if(!StringUtils.isEmpty(areaManagerDto.getProvinceId())){
            Category province = new Category();
            province.setId(areaManagerDto.getProvinceId());
            savedAreaManager.setProvince(province);
        }
        if(!StringUtils.isEmpty(areaManagerDto.getCityId())){
            Category city = new Category();
            city.setId(areaManagerDto.getCityId());
            savedAreaManager.setCity(city);
        }
        if(!StringUtils.isEmpty(areaManagerDto.getRegionId())){
            Category region = new Category();
            region.setId(areaManagerDto.getRegionId());
            savedAreaManager.setRegion(region);
        }
        areaManagerService.updateObj(savedAreaManager);
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
            User user = userService.queryUserByUsername(name);
            if(user==null) {
                return new JsonResult(Result.SUCCESS);
            }
        }
        return new JsonResult(Result.FAIL);
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
