package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.AccidentRecordDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.AccidentRecord;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IAccidentRecordService;
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
 * 事故记录及处理控制器
 */
@RestController
public class AccidentRecordController extends CommonController{
    @Autowired
    private IAccidentRecordService accidentRecordService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param accidentRecordDto
     * @return
     */
    @GetMapping("/accidentRecord/accidentRecordsByPage")
    public ModelMap queryAccidentRecords(AccidentRecordDto accidentRecordDto){
        Page page = accidentRecordService.findAccidentRecords(accidentRecordDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param accidentRecordDto
     * @return
     */
    @PostMapping("/accidentRecord/accidentRecord")
    public JsonResult addAccidentRecord(AccidentRecordDto accidentRecordDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        AccidentRecord savedAccidentRecord = new AccidentRecord();
        BeanUtils.copyProperties(accidentRecordDto,savedAccidentRecord);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "accidentRecord";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedAccidentRecord.setUrl(urlMapping);
            savedAccidentRecord.setRealPath(savedFile.getAbsolutePath());
            savedAccidentRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedAccidentRecord.setCreateDate(new Date());
        savedAccidentRecord.setOrg(getOrg(request));
        savedAccidentRecord.setCreator(getCurrentUsername(request));
        accidentRecordService.addObj(savedAccidentRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param accidentRecordDto
     * @return
     */
    @PostMapping("/accidentRecord/updateAccidentRecord")
    public JsonResult updateAccidentRecord(AccidentRecordDto accidentRecordDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        AccidentRecord savedAccidentRecord = accidentRecordService.queryObjById(accidentRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "accidentRecord";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedAccidentRecord.getRealPath())){
                FileUtil.deleteFile(savedAccidentRecord.getRealPath());
            }
            savedAccidentRecord.setUrl(urlMapping);
            savedAccidentRecord.setRealPath(savedFile.getAbsolutePath());
            savedAccidentRecord.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedAccidentRecord.setName(accidentRecordDto.getName());
        savedAccidentRecord.setNote(accidentRecordDto.getNote());
        accidentRecordService.updateObj(savedAccidentRecord);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param accidentRecordDto
     * @return
     */
    @PostMapping("/accidentRecord/updateAccidentRecordNoFile")
    public JsonResult updateAccidentRecordNoFile(AccidentRecordDto accidentRecordDto, HttpServletRequest request){
        AccidentRecord savedAccidentRecord = accidentRecordService.queryObjById(accidentRecordDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedAccidentRecord.setName(accidentRecordDto.getName());
        savedAccidentRecord.setNote(accidentRecordDto.getNote());
        accidentRecordService.updateObj(savedAccidentRecord);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/accidentRecord/accidentRecord/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        accidentRecordService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
