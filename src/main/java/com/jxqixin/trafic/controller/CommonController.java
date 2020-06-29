package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * Created by Administrator on 2019/10/6.
 */
@Controller
public class CommonController {
    @Value("${uploadFile.urlMapping}")
    private String urlMapping;
    @Value("${uploadFile.resourceLocation}")
    private String resourceLocation;
    @Autowired
    private RedisUtil redisUtil;

    public String getUrlMapping() {
        return urlMapping;
    }

    public void setUrlMapping(String urlMapping) {
        this.urlMapping = urlMapping;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }
    /***
     * 分页返回时使用的ModelMap
     * @param page
     * @return
     */
    public ModelMap pageModelMap(Page page){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("code",0);
        modelMap.addAttribute("count",page.getTotalElements());
        modelMap.addAttribute("msg","");
        modelMap.addAttribute("data",page.getContent());
        return modelMap;
    }
    /**
     * 获取操作成功ModelMap
     * @param message
     * @return
     */
    public ModelMap successModelMap(String message){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("statusCode",200);
        modelMap.addAttribute("success",true);
        modelMap.addAttribute("message",message);
        return modelMap;
    }
    /**
     * 获取操作失败ModelMap
     * @param message
     * @return
     */
    public ModelMap failureModelMap(String message){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("statusCode",300);
        modelMap.addAttribute("success",false);
        modelMap.addAttribute("message",message);
        return modelMap;
    }
    /**
     * 文件上传
     * @param dir 文件存储路径，相对于application.yaml中的upload.location目录
     * @param file 上传的文件
     * @return
     */
    public File upload(String dir, MultipartFile file) throws IOException {
        File uploadDir = new File(resourceLocation + dir);
        if(!uploadDir.exists()){
            uploadDir.mkdirs();
        }
        String filename = file.getOriginalFilename();
        String ext = filename.substring(filename.lastIndexOf("."),filename.length());
        filename = UUID.randomUUID().toString() + ext;
        File savedFile = new File(uploadDir + "/" + filename);
        file.transferTo(savedFile);
        return savedFile;
    }

    /**
     * 获取当前登录用户的用户名
     * @param request
     * @return
     */
    public String getCurrentUsername(HttpServletRequest request){
        String token = request.getHeader("token");
        return  (String)redisUtil.get(token);
    }
}
