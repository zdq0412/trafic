package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceMaintainDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.DeviceMaintain;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IDeviceMaintainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器
 */
@RestController
public class DeviceMaintainController extends CommonController{
    @Autowired
    private IDeviceMaintainService deviceMaintainService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param deviceMaintainDto
     * @return
     */
    @GetMapping("/deviceMaintain/deviceMaintainsByPage")
    public ModelMap queryDeviceMaintains(DeviceMaintainDto deviceMaintainDto){
        Page page = deviceMaintainService.findDeviceMaintains(deviceMaintainDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param deviceMaintainDto
     * @return
     */
    @PostMapping("/deviceMaintain/deviceMaintain")
    public JsonResult addDeviceMaintain(DeviceMaintainDto deviceMaintainDto){
        DeviceMaintain deviceMaintain = new DeviceMaintain();
        BeanUtils.copyProperties(deviceMaintainDto,deviceMaintain);
        deviceMaintain.setCreateDate(new Date());
        try {
            deviceMaintain.setOperDate(format.parse(deviceMaintainDto.getOperDate()));
        } catch (Exception e) {
            deviceMaintain.setOperDate(null);
        }
        if(!StringUtils.isEmpty(deviceMaintainDto.getDeviceId())){
            Device device = new Device();
            device.setId(deviceMaintainDto.getDeviceId());

            deviceMaintain.setDevice(device);
        }
        deviceMaintainService.addObj(deviceMaintain);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param deviceMaintainDto
     * @return
     */
    @PutMapping("/deviceMaintain/deviceMaintain")
    public JsonResult updateDeviceMaintain(DeviceMaintainDto deviceMaintainDto){
        DeviceMaintain savedDeviceMaintain = deviceMaintainService.queryObjById(deviceMaintainDto.getId());
        savedDeviceMaintain.setNote(deviceMaintainDto.getNote());
        try {
            savedDeviceMaintain.setOperDate(format.parse(deviceMaintainDto.getOperDate()));
        } catch (Exception e) {
            savedDeviceMaintain.setOperDate(null);
        }
        savedDeviceMaintain.setPrice(deviceMaintainDto.getPrice());
        savedDeviceMaintain.setContent(deviceMaintainDto.getContent());
        deviceMaintainService.updateObj(savedDeviceMaintain);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/deviceMaintain/deviceMaintain/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceMaintainService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
