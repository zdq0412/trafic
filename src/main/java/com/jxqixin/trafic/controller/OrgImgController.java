package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgImg;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IOrgImgService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

/**
 * 企业图片文件控制器
 */
@RestController
public class OrgImgController extends CommonController{
    @Autowired
    private IOrgImgService orgImgService;
    @Autowired
    private IUserService userService;
    /**
     * 查询所有企业图片文件
     * @return
     */
    @GetMapping("/orgImg/orgImgs")
    public JsonResult<List<OrgImg>> queryAllOrgImg(HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        List<OrgImg> list = orgImgService.findAll(user.getOrg());
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 新增企业图片文件
     * @param orgImg
     * @return
     */
    @PostMapping("/orgImg/orgImg")
    public JsonResult addOrgImg(OrgImg orgImg){
        orgImg.setId(UUID.randomUUID().toString());
        orgImgService.addObj(orgImg);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑企业图片文件
     * @param orgImg
     * @return
     */
    @PutMapping("/orgImg/orgImg")
    public JsonResult updateOrgImg(OrgImg orgImg){
        OrgImg savedOrgImg = orgImgService.queryObjById(orgImg.getId());
        savedOrgImg.setName(orgImg.getName());
        orgImgService.updateObj(savedOrgImg);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除企业图片文件
     * @param id
     * @return
     */
    @DeleteMapping("/orgImg/orgImg/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            orgImgService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
