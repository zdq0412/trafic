package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmergencyPlanBakDto;
import com.jxqixin.trafic.model.EmergencyPlanBak;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IEmergencyPlanBakService;
import com.jxqixin.trafic.service.IPreplanDrillRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 应急预案备案控制器
 */
@RestController
public class EmergencyPlanBakController extends CommonController{
    @Autowired
    private IEmergencyPlanBakService emergencyPlanBakService;
    @Autowired
    private IPreplanDrillRecordService preplanDrillRecordService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param emergencyPlanBakDto
     * @return
     */
    @GetMapping("/emergencyPlanBak/emergencyPlanBaksByPage")
    public ModelMap queryEmergencyPlanBaks(EmergencyPlanBakDto emergencyPlanBakDto, HttpServletRequest request){
        Page page = emergencyPlanBakService.findEmergencyPlanBaks(emergencyPlanBakDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param emergencyPlanBakDto
     * @return
     */
    @PostMapping("/emergencyPlanBak/emergencyPlanBak")
    public JsonResult addEmergencyPlanBak(EmergencyPlanBakDto emergencyPlanBakDto,HttpServletRequest request){
        EmergencyPlanBak emergencyPlanBak = new EmergencyPlanBak();
        BeanUtils.copyProperties(emergencyPlanBakDto,emergencyPlanBak);
        try {
            emergencyPlanBak.setWriteDate(format.parse(emergencyPlanBakDto.getWriteDate()));
        } catch (Exception e) {
            emergencyPlanBak.setWriteDate(null);
        }
        emergencyPlanBak.setOrg(getOrg(request));
        emergencyPlanBak.setCreateDate(new Date());
        emergencyPlanBakService.addObj(emergencyPlanBak);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param emergencyPlanBakDto
     * @return
     */
    @PutMapping("/emergencyPlanBak/emergencyPlanBak")
    public JsonResult updateEmergencyPlanBak(EmergencyPlanBakDto emergencyPlanBakDto){
        EmergencyPlanBak savedEmergencyPlanBak = emergencyPlanBakService.queryObjById(emergencyPlanBakDto.getId());
        try {
            savedEmergencyPlanBak.setWriteDate(format.parse(emergencyPlanBakDto.getWriteDate()));
        } catch (Exception e) {
            savedEmergencyPlanBak.setWriteDate(null);
        }
        try {
            savedEmergencyPlanBak.setKeepOnRecordDate(format.parse(emergencyPlanBakDto.getKeepOnRecordDate()));
        } catch (Exception e) {
            savedEmergencyPlanBak.setKeepOnRecordDate(null);
        }
        savedEmergencyPlanBak.setPrePlanName(emergencyPlanBakDto.getPrePlanName());
        savedEmergencyPlanBak.setKeepOnRecordName(emergencyPlanBakDto.getKeepOnRecordName());
        emergencyPlanBakService.updateObj(savedEmergencyPlanBak);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 备案
     * @param emergencyPlanBakDto
     * @return
     */
    @PutMapping("/emergencyPlanBak/preplan")
    public JsonResult preplan(EmergencyPlanBakDto emergencyPlanBakDto){
        EmergencyPlanBak savedEmergencyPlanBak = emergencyPlanBakService.queryObjById(emergencyPlanBakDto.getId());
        try {
            savedEmergencyPlanBak.setKeepOnRecordDate(format.parse(emergencyPlanBakDto.getKeepOnRecordDate()));
        } catch (Exception e) {
            savedEmergencyPlanBak.setKeepOnRecordDate(null);
        }
        savedEmergencyPlanBak.setKeepOnRecordName(emergencyPlanBakDto.getKeepOnRecordName());
        savedEmergencyPlanBak.setKeepOnRecord(true);
        emergencyPlanBakService.updateObj(savedEmergencyPlanBak);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/emergencyPlanBak/emergencyPlanBak/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        emergencyPlanBakService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID查找应急演练
     * @param id
     * @return
     */
    @GetMapping("/emergencyPlanBak/preplanDrillRecord/{id}")
    public JsonResult preplanDrillRecord(@PathVariable(name="id") String id){
       Long count = preplanDrillRecordService.queryCountByEmergencyPlanBakId(id);
       if(count == null || count<=0){
           return new JsonResult(Result.SUCCESS,false);
       }
        return new JsonResult(Result.SUCCESS,true);
    }
}
