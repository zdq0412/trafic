package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.CommonPositionDto;
import com.jxqixin.trafic.model.CommonPosition;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.ICommonPositionService;
import com.jxqixin.trafic.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 通用职位管理
 */
@RestController
public class CommonPositionController extends CommonController{
    @Autowired
    private ICommonPositionService commonPositionService;
    /**
     * 分页查询通用职位
     * @param commonPositionDto
     * @return
     */
    @GetMapping("/commonPosition/commonPositionsByPage")
    public ModelMap queryCommonPositions(CommonPositionDto commonPositionDto){
        Page page = commonPositionService.findCommonPositions(commonPositionDto);
        return pageModelMap(page);
    }
    /**
     * 新增通用职位
     * @param commonPositionDto
     * @return
     */
    @PostMapping("/commonPosition/commonPosition")
    public JsonResult addCommonPosition(CommonPositionDto commonPositionDto){
        CommonPosition commonPosition = new CommonPosition();
        BeanUtils.copyProperties(commonPositionDto,commonPosition);
        commonPosition.setCreateDate(new Date());
        commonPositionService.addObj(commonPosition);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 企业类别授权查询
     * @return
     */
    @GetMapping("/commonPositions/orgCategoryCommonPositions")
    public ModelMap findOrgCategoryCommonPositions(String orgCategoryId){
        List<CommonPosition> commonPositions = commonPositionService.findAllCommonPositions();
        List<String> commonPositionsIdList = commonPositionService.findIdsByOrgCategoryId(orgCategoryId);
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("commonPositions",commonPositions);
        modelMap.addAttribute("commonPositionIds",commonPositionsIdList);
        return modelMap;
    }
    /**
     * 根据企业类别ID查找预设职位
     * @return
     */
    @GetMapping("/commonPositions/commonPositions")
    public List<CommonPosition> commonPositions(HttpServletRequest request){
        Org org = getOrg(request);
        if(org==null){
            return new ArrayList<>();
        }else{
            OrgCategory orgCategory = org.getOrgCategory();
            if(orgCategory==null){
                return new ArrayList<>();
            }else{
                return commonPositionService.findByOrgCategoryId(orgCategory.getId());
            }
        }
    }
    /**
     * 为企业类别赋预设职位
     * @param commonPositionsId
     * @param orgCategoryId
     * @return
     */
    @PutMapping("/commonPosition/commonPositionOrgCategorys")
    public JsonResult assignFunctions2OrgCategory(String commonPositionsId,String orgCategoryId){
        String[] commonPositionIdArray = StringUtil.handleIds(commonPositionsId);
        commonPositionService.assign2OrgCategory(commonPositionIdArray,orgCategoryId);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑通用职位
     * @param commonPositionDto
     * @return
     */
    @PostMapping("/commonPosition/updateCommonPosition")
    public JsonResult updateCommonPosition(CommonPositionDto commonPositionDto){
        CommonPosition commonPosition = commonPositionService.queryObjById(commonPositionDto.getId());
        commonPosition.setName(commonPosition.getName());
        commonPositionService.updateObj(commonPosition);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除通用职位
     * @param id
     * @return
     */
    @DeleteMapping("/commonPosition/commonPosition/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            commonPositionService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
