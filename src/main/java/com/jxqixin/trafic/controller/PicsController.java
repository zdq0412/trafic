package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Pics;
import com.jxqixin.trafic.service.IPicsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * 控制器
 */
@RestController
public class PicsController extends CommonController{
    @Autowired
    private IPicsService picsService;
    /**
     * 查询
     * @return
     */
    @GetMapping("/pics/pics")
    public List<Pics> queryPicss(String type,String pid){
        List<Pics> list = picsService.findPics(type,pid);
        return list;
    }
    /**
     * 上传照片(会议、培训和日常检查等)
     * @param files
     * @param request
     * @param type 文件所属类别
     * @param pid 文件所属类别记录ID
     * @return
     */
    @PostMapping("/pics/upload")
    public JsonResult fileUpload(@RequestParam("files")MultipartFile[] files, HttpServletRequest request,String type,String pid){
        if(StringUtils.isEmpty(type) || StringUtils.isEmpty(pid)){
            return new JsonResult(Result.FAIL);
        }
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (getOrg(request)==null?"":(getOrg(request)+"/")) + "photos";
            if(files!=null &&files.length>0){
                for(MultipartFile file : files){
                    File savedFile = upload(dir,file);
                    if(savedFile!=null) {
                        urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
                    }
                    Pics pics = new Pics();
                    pics.setPid(pid);
                    pics.setRealPath(savedFile.getAbsolutePath());
                    pics.setType(type);
                    pics.setUrl(urlMapping);
                    picsService.addObj(pics);
                }
            }
        } catch (RuntimeException | IOException e) {
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/pics/pics/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        picsService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据类别和类别ID删除照片
     * @param type 照片所属类别
     * @param pid  所属类别ID
     * @return
     */
    @DeleteMapping("/pics/deleteAll")
    public JsonResult deleteById(String type,String pid){
        picsService.deleteAll(type,pid);
        return new JsonResult(Result.SUCCESS);
    }
}
