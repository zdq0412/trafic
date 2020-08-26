package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceCheckDto;
import com.jxqixin.trafic.model.DeviceCheck;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IDeviceCheckService;
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
 * 设备检查控制器
 */
@RestController
public class DeviceCheckController extends CommonController{
    @Autowired
    private IDeviceCheckService deviceCheckService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param deviceCheckDto
     * @return
     */
    @GetMapping("/deviceCheck/deviceChecksByPage")
    public ModelMap queryDeviceChecks(DeviceCheckDto deviceCheckDto,HttpServletRequest request){
        Page page = deviceCheckService.findDeviceChecks(deviceCheckDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param deviceCheckDto
     * @return
     */
    @PostMapping("/deviceCheck/deviceCheck")
    public JsonResult addDeviceCheck(DeviceCheckDto deviceCheckDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        DeviceCheck savedDeviceCheck = new DeviceCheck();
        BeanUtils.copyProperties(deviceCheckDto,savedDeviceCheck);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "deviceCheck";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedDeviceCheck.setUrl(urlMapping);
            savedDeviceCheck.setRealPath(savedFile.getAbsolutePath());
            savedDeviceCheck.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedDeviceCheck.setCreateDate(new Date());
        savedDeviceCheck.setOrg(getOrg(request));
        savedDeviceCheck.setCreator(getCurrentUsername(request));
        deviceCheckService.addObj(savedDeviceCheck);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param deviceCheckDto
     * @return
     */
    @PostMapping("/deviceCheck/updateDeviceCheck")
    public JsonResult updateDeviceCheck(DeviceCheckDto deviceCheckDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        DeviceCheck savedDeviceCheck = deviceCheckService.queryObjById(deviceCheckDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "deviceCheck";// 年度运行台账
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedDeviceCheck.getRealPath())){
                FileUtil.deleteFile(savedDeviceCheck.getRealPath());
            }
            savedDeviceCheck.setUrl(urlMapping);
            savedDeviceCheck.setRealPath(savedFile.getAbsolutePath());
            savedDeviceCheck.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedDeviceCheck.setName(deviceCheckDto.getName());
        savedDeviceCheck.setNote(deviceCheckDto.getNote());
        deviceCheckService.updateObj(savedDeviceCheck);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param deviceCheckDto
     * @return
     */
    @PostMapping("/deviceCheck/updateDeviceCheckNoFile")
    public JsonResult updateDeviceCheckNoFile(DeviceCheckDto deviceCheckDto, HttpServletRequest request){
        DeviceCheck savedDeviceCheck = deviceCheckService.queryObjById(deviceCheckDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        savedDeviceCheck.setName(deviceCheckDto.getName());
        savedDeviceCheck.setNote(deviceCheckDto.getNote());
        deviceCheckService.updateObj(savedDeviceCheck);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/deviceCheck/deviceCheck/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceCheckService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
