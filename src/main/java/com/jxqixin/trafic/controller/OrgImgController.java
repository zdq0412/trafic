package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.OrgImgDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgImg;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IOrgImgService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 企业图片文件控制器
 */
@RestController
public class OrgImgController extends CommonController{
    private SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

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
    public JsonResult updateOrgImg(OrgImgDto orgImg){
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
    /**
     * 上传公司图片
     * @return
     */
    @PostMapping("/orgImg/photos")
    public JsonResult uploadImages(@RequestParam("file") MultipartFile[] files,String name, String orgId, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        List<OrgImg> orgImgList = new ArrayList<>();
        List<String> urls = new ArrayList<>();
        Org org = new Org();
        org.setId(orgId);
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "photo";
            if(files.length>0) {
                for(int i = 0;i<files.length;i++) {
                    File savedFile = upload(dir, files[i]);
                    if (savedFile != null) {
                        urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
                    }
                    OrgImg orgImg = new OrgImg();
                    orgImg.setOrg(org);
                    orgImg.setName(name);
                    orgImg.setUploadDate(new Date());
                    orgImg.setUrl(urlMapping);
                    orgImg.setRealPath(savedFile.getAbsolutePath());

                    urls.add(urlMapping);
                    orgImgList.add(orgImg);
                }

                orgImgService.saveAll(orgImgList);
            }
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urls);
    }
}
