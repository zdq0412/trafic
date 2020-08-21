package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.GPSAccountDto;
import com.jxqixin.trafic.model.GPSAccount;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IGPSAccountService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * GPS运行台账控制器
 */
@RestController
public class GPSAccountController extends CommonController{
    @Autowired
    private IGPSAccountService gpsAccountService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询
     * @param gpsAccountDto
     * @return
     */
    @GetMapping("/gpsAccount/gpsAccountsByPage")
    public ModelMap queryGPSAccounts(GPSAccountDto gpsAccountDto, HttpServletRequest request){
        Page page = gpsAccountService.findGPSAccounts(gpsAccountDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param gpsAccountDto
     * @return
     */
    @PostMapping("/gpsAccount/gpsAccount")
    public JsonResult addGPSAccount(GPSAccountDto gpsAccountDto,HttpServletRequest request){
        GPSAccount gpsAccount = new GPSAccount();
        BeanUtils.copyProperties(gpsAccountDto,gpsAccount);
        try {
            gpsAccount.setDriveDate(format.parse(gpsAccountDto.getDriveDate()));
        } catch (Exception e) {
            gpsAccount.setDriveDate(null);
        }
        gpsAccount.setOrg(getOrg(request));
        gpsAccount.setCreateDate(new Date());
        gpsAccountService.addObj(gpsAccount);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param gpsAccountDto
     * @return
     */
    @PutMapping("/gpsAccount/gpsAccount")
    public JsonResult updateGPSAccount(GPSAccountDto gpsAccountDto){
        GPSAccount savedGPSAccount = gpsAccountService.queryObjById(gpsAccountDto.getId());
        try {
            savedGPSAccount.setDriveDate(format.parse(gpsAccountDto.getDriveDate()));
        } catch (Exception e) {
            savedGPSAccount.setDriveDate(null);
        }
        savedGPSAccount.setCarNum(gpsAccountDto.getCarNum());
        savedGPSAccount.setCarStatus(gpsAccountDto.getCarStatus());
        savedGPSAccount.setDriverName(gpsAccountDto.getDriverName());
        savedGPSAccount.setGoodsName(gpsAccountDto.getGoodsName());
        savedGPSAccount.setGpsStatus(gpsAccountDto.getGpsStatus());
        savedGPSAccount.setIllegal(gpsAccountDto.getIllegal());
        savedGPSAccount.setDriveLines(gpsAccountDto.getDriveLines());
        savedGPSAccount.setNote(gpsAccountDto.getNote());
        gpsAccountService.updateObj(savedGPSAccount);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/gpsAccount/gpsAccount/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        gpsAccountService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
