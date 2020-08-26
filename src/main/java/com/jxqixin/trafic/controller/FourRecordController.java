package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.FourRecordDto;
import com.jxqixin.trafic.model.FourRecord;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IFourRecordService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 四不放过控制器
 */
@RestController
public class FourRecordController extends CommonController{
    @Autowired
    private IFourRecordService fourRecordService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param fourRecordDto
     * @return
     */
    @GetMapping("/fourRecord/fourRecordsByPage")
    public ModelMap queryFourRecords(FourRecordDto fourRecordDto,HttpServletRequest request){
        Page page = fourRecordService.findFourRecords(fourRecordDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param fourRecordDto
     * @return
     */
    @PostMapping("/fourRecord/fourRecord")
    public JsonResult addFourRecord(FourRecordDto fourRecordDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        FourRecord savedFourRecord = new FourRecord();
        BeanUtils.copyProperties(fourRecordDto,savedFourRecord);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "fourRecord";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedFourRecord.setUrl(urlMapping);
            savedFourRecord.setRealPath(savedFile.getAbsolutePath());
            savedFourRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedFourRecord.setCreateDate(new Date());
        savedFourRecord.setOrg(getOrg(request));
        savedFourRecord.setCreator(getCurrentUsername(request));
        fourRecordService.addObj(savedFourRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param fourRecordDto
     * @return
     */
    @PostMapping("/fourRecord/updateFourRecord")
    public JsonResult updateFourRecord(FourRecordDto fourRecordDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        FourRecord savedFourRecord = fourRecordService.queryObjById(fourRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "fourRecord";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedFourRecord.getRealPath())){
                FileUtil.deleteFile(savedFourRecord.getRealPath());
            }
            savedFourRecord.setUrl(urlMapping);
            savedFourRecord.setRealPath(savedFile.getAbsolutePath());
            savedFourRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedFourRecord.setName(fourRecordDto.getName());
        savedFourRecord.setNote(fourRecordDto.getNote());
        fourRecordService.updateObj(savedFourRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param fourRecordDto
     * @return
     */
    @PostMapping("/fourRecord/updateFourRecordNoFile")
    public JsonResult updateFourRecordNoFile(FourRecordDto fourRecordDto, HttpServletRequest request){
        FourRecord savedFourRecord = fourRecordService.queryObjById(fourRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedFourRecord.setName(fourRecordDto.getName());
        savedFourRecord.setNote(fourRecordDto.getNote());
        fourRecordService.updateObj(savedFourRecord);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/fourRecord/fourRecord/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        fourRecordService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
