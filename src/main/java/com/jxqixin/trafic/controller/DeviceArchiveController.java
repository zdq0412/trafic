package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceArchiveDto;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.DeviceArchive;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IDeviceArchiveService;
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
public class DeviceArchiveController extends CommonController{
    @Autowired
    private IDeviceArchiveService deviceArchiveService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param deviceArchiveDto
     * @return
     */
    @GetMapping("/deviceArchive/deviceArchivesByPage")
    public ModelMap queryDeviceArchives(DeviceArchiveDto deviceArchiveDto){
        Page page = deviceArchiveService.findDeviceArchives(deviceArchiveDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param deviceArchiveDto
     * @return
     */
    @PostMapping("/deviceArchive/deviceArchive")
    public JsonResult addDeviceArchive(DeviceArchiveDto deviceArchiveDto){
        DeviceArchive deviceArchive = new DeviceArchive();
        BeanUtils.copyProperties(deviceArchiveDto,deviceArchive);
        deviceArchive.setCreateDate(new Date());
        try {
            deviceArchive.setBeginDate(format.parse(deviceArchiveDto.getBeginDate()));
        } catch (Exception e) {
            deviceArchive.setBeginDate(null);
        }
        try {
            deviceArchive.setEndDate(format.parse(deviceArchiveDto.getEndDate()));
        } catch (Exception e) {
            deviceArchive.setEndDate(null);
        }
        if(!StringUtils.isEmpty(deviceArchiveDto.getDeviceId())){
            Device device = new Device();
            device.setId(deviceArchiveDto.getDeviceId());

            deviceArchive.setDevice(device);
        }
        deviceArchiveService.addDeviceArchive(deviceArchive);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param deviceArchiveDto
     * @return
     */
    @PostMapping("/deviceArchive/updateDeviceArchive")
    public JsonResult updateDeviceArchive(DeviceArchiveDto deviceArchiveDto){
        DeviceArchive savedDeviceArchive = deviceArchiveService.queryObjById(deviceArchiveDto.getId());
        savedDeviceArchive.setNote(deviceArchiveDto.getNote());
        try {
            savedDeviceArchive.setBeginDate(format.parse(deviceArchiveDto.getBeginDate()));
        } catch (Exception e) {
            savedDeviceArchive.setBeginDate(null);
        }
        try {
            savedDeviceArchive.setEndDate(format.parse(deviceArchiveDto.getEndDate()));
        } catch (Exception e) {
            savedDeviceArchive.setEndDate(null);
        }
        savedDeviceArchive.setMoney(deviceArchiveDto.getMoney());
        savedDeviceArchive.setSupplier(deviceArchiveDto.getSupplier());
        savedDeviceArchive.setName(deviceArchiveDto.getName());
        deviceArchiveService.updateObj(savedDeviceArchive);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/deviceArchive/deviceArchive/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceArchiveService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
