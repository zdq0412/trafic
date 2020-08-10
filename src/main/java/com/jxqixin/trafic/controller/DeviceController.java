package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DeviceDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.Device;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IDeviceService;
import com.jxqixin.trafic.service.IUserService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 设备控制器
 */
@RestController
public class DeviceController extends CommonController{
    @Autowired
    private IDeviceService deviceService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询设备
     * @param deviceDto
     * @return
     */
    @GetMapping("/device/devicesByPage")
    public ModelMap queryDevices(DeviceDto deviceDto,HttpServletRequest request){
        Page page = deviceService.findDevices(deviceDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增设备
     * @param deviceDto
     * @return
     */
  /*  @PostMapping("/device/addDevice")
    public JsonResult addDevice(DeviceDto deviceDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "device";
            File savedFile = upload(dir,file);
            if(savedFile!=null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            deviceDto.setUrl(urlMapping);
            deviceDto.setRealPath(savedFile.getAbsolutePath());
            Device device = new Device();
            BeanUtils.copyProperties(deviceDto,device);
            if(!StringUtils.isEmpty(deviceDto.getCategoryId())){
                Category category = new Category();
                category.setId(deviceDto.getCategoryId());

                device.setCategory(category);
            }
            device.setCreateDate(new Date());
            device.setOrg(getOrg(request));
            deviceService.addObj(device);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urlMapping);
    }*/
    /**
     * 新增设备，不上传头像
     * @param deviceDto
     * @return
     */
    @PostMapping("/device/addDeviceNoPhoto")
    public JsonResult addDeviceNoPhoto(DeviceDto deviceDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        try {
            Device device = new Device();
            BeanUtils.copyProperties(deviceDto,device);
            if(!StringUtils.isEmpty(deviceDto.getCategoryId())){
                Category category = new Category();
                category.setId(deviceDto.getCategoryId());

                device.setCategory(category);
            }
            if(!StringUtils.isEmpty(deviceDto.getBuyDate())){
                try {
                    device.setBuyDate(format.parse(deviceDto.getBuyDate()));
                } catch (ParseException e) {
                    deviceDto.setBuyDate(null);
                }
            }
            device.setCreateDate(new Date());
            device.setOrg(getOrg(request));
            deviceService.addObj(device);
        } catch (RuntimeException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }
    /**
     * 编辑设备
     * @param deviceDto
     * @return
     */
    @PostMapping("/device/updateDevice")
    public JsonResult updateDevice(DeviceDto deviceDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "device";
            File savedFile = upload(dir,file);
            if(savedFile!=null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            Device device = deviceService.queryObjById(deviceDto.getId());
            if(!StringUtils.isEmpty(deviceDto.getCategoryId())){
                Category category = new Category();
                category.setId(deviceDto.getCategoryId());
                device.setCategory(category);
            }
            try {
                device.setBuyDate(format.parse(deviceDto.getBuyDate()));
            } catch (Exception e) {
                device.setBuyDate(null);
            }

            device.setEquipmentCode(deviceDto.getEquipmentCode());
            device.setManufacturer(deviceDto.getManufacturer());
            device.setName(deviceDto.getName());
            device.setNote(deviceDto.getNote());
            device.setPosition(deviceDto.getPosition());
            device.setPrice(deviceDto.getPrice());
            device.setSpecification(deviceDto.getSpecification());
            deviceService.updateObj(device);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }
    /**
     * 编辑设备,不修改头像
     * @param deviceDto
     * @return
     */
    @PostMapping("/device/updateDeviceNoPhoto")
    public JsonResult updateDeviceNoPhoto(DeviceDto deviceDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        try {
            Device device = deviceService.queryObjById(deviceDto.getId());
            if(!StringUtils.isEmpty(deviceDto.getCategoryId())){
                Category category = new Category();
                category.setId(deviceDto.getCategoryId());
                device.setCategory(category);
            }
            try {
                device.setBuyDate(format.parse(deviceDto.getBuyDate()));
            } catch (Exception e) {
                device.setBuyDate(null);
            }
            device.setEquipmentCode(deviceDto.getEquipmentCode());
            device.setManufacturer(deviceDto.getManufacturer());
            device.setName(deviceDto.getName());
            device.setNote(deviceDto.getNote());
            device.setPosition(deviceDto.getPosition());
            device.setPrice(deviceDto.getPrice());
            device.setSpecification(deviceDto.getSpecification());
            deviceService.updateObj(device);
        } catch (RuntimeException  e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }
    /**
     * 根据ID删除设备
     * @param id
     * @return
     */
    @DeleteMapping("/device/device/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        deviceService.deleteDevice(id);
        return new JsonResult(Result.SUCCESS);
    }
}
