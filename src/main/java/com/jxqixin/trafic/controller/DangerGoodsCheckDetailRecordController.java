package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.DangerGoodsCheckDetailRecordDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.DangerGoodsCheckDetailRecord;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.service.IDangerGoodsCheckDetailRecordService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 危险货物运输企业安全生产隐患排查整改台账模板详情控制器
 */
@RestController
public class DangerGoodsCheckDetailRecordController extends CommonController{
    @Autowired
    private IDangerGoodsCheckDetailRecordService dangerGoodsCheckDetailRecordService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 隐患排查和治理情况分析
     * @param checkDateFrom
     * @param checkDateTo
     * @return
     */
    @GetMapping("/dangerGoodsCheckDetailRecord/analysis")
    public Object[] analysis(String checkDateFrom,String checkDateTo,HttpServletRequest request){
        return dangerGoodsCheckDetailRecordService.analysis(checkDateFrom,checkDateTo,getOrg(request));
    }
    /**
     * 隐患类型统计
     * @param checkDateFrom
     * @param checkDateTo
     * @return
     */
    @GetMapping("/dangerGoodsCheckDetailRecord/statistics")
    public Object[] statistics(String checkDateFrom,String checkDateTo,HttpServletRequest request){
        return dangerGoodsCheckDetailRecordService.statistics(checkDateFrom,checkDateTo,getOrg(request));
    }

    /**
     * 整改
     * @param id
     * @param rectificationFund
     * @return
     */
    @GetMapping("/dangerGoodsCheckDetailRecord/rectificate")
   public JsonResult rectificate(String id, BigDecimal rectificationFund){
       DangerGoodsCheckDetailRecord record = dangerGoodsCheckDetailRecordService.queryObjById(id);
       record.setRectificationFund(rectificationFund);
       record.setRectification(true);

       dangerGoodsCheckDetailRecordService.updateObj(record);
       return new JsonResult(Result.SUCCESS);
   }

    /**
     * 根据条件查询隐患排查整改记录
     * @return
     */
    @GetMapping("/dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecords")
    public JsonResult<List<DangerGoodsCheckDetailRecord>> queryAllDangerGoodsCheckDetailRecord(DangerGoodsCheckDetailRecordDto recordDto,HttpServletRequest request){
        List<DangerGoodsCheckDetailRecord> list = null;
        try {
            list = dangerGoodsCheckDetailRecordService.findDangerGoodsCheckDetailRecordsNoPage(recordDto,getOrg(request));
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult<>(result);
        }
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询隐患排查整改记录
     * @param record
     * @return
     */
    @GetMapping("/dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecordsByPage")
    public ModelMap queryDangerGoodsCheckDetailRecords(DangerGoodsCheckDetailRecordDto record,HttpServletRequest request){
        Page page = dangerGoodsCheckDetailRecordService.findDangerGoodsCheckDetailRecords(record,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增隐患排查整改记录
     * @param dangerGoodsCheckDetailRecordDto
     * @return
     */
    @PostMapping("/dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecord")
    public JsonResult addDangerGoodsCheckDetailRecord(DangerGoodsCheckDetailRecordDto dangerGoodsCheckDetailRecordDto, HttpServletRequest request){
        DangerGoodsCheckDetailRecord dangerGoodsCheckDetailRecord = new DangerGoodsCheckDetailRecord();
        BeanUtils.copyProperties(dangerGoodsCheckDetailRecordDto,dangerGoodsCheckDetailRecord);
        setProperties(dangerGoodsCheckDetailRecord,dangerGoodsCheckDetailRecordDto);
        dangerGoodsCheckDetailRecord.setOrg(getOrg(request));
        dangerGoodsCheckDetailRecordService.addObj(dangerGoodsCheckDetailRecord);
        return new JsonResult(Result.SUCCESS);
    }

    private void setProperties(DangerGoodsCheckDetailRecord dangerGoodsCheckDetailRecord,DangerGoodsCheckDetailRecordDto dangerGoodsCheckDetailRecordDto){
        dangerGoodsCheckDetailRecord.setCheckedOrg(dangerGoodsCheckDetailRecordDto.getCheckedOrg());
        dangerGoodsCheckDetailRecord.setCorrectiveAction(dangerGoodsCheckDetailRecordDto.getCorrectiveAction());
        dangerGoodsCheckDetailRecord.setHiddenDanger(dangerGoodsCheckDetailRecordDto.getHiddenDanger());
        dangerGoodsCheckDetailRecord.setPerson(dangerGoodsCheckDetailRecordDto.getPerson());
        dangerGoodsCheckDetailRecord.setDetailNote(dangerGoodsCheckDetailRecordDto.getDetailNote());
        dangerGoodsCheckDetailRecord.setTimelimit(dangerGoodsCheckDetailRecordDto.getTimelimit());
        try {
            dangerGoodsCheckDetailRecord.setCheckDate(format.parse(dangerGoodsCheckDetailRecordDto.getCheckDate()));
        } catch (Exception e) {
            dangerGoodsCheckDetailRecord.setCheckDate(null);
        }
        try {
            dangerGoodsCheckDetailRecord.setCancelDate(format.parse(dangerGoodsCheckDetailRecordDto.getCancelDate()));
        } catch (Exception e) {
            dangerGoodsCheckDetailRecord.setCancelDate(null);
        }
        try {
            dangerGoodsCheckDetailRecord.setEndTime(format.parse(dangerGoodsCheckDetailRecordDto.getEndTime()));
        } catch (Exception e) {
            dangerGoodsCheckDetailRecord.setEndTime(null);
        }

        if(!StringUtils.isEmpty(dangerGoodsCheckDetailRecordDto.getSeverityId())){
            Category severity = new Category();
            severity.setId(dangerGoodsCheckDetailRecordDto.getSeverityId());

            dangerGoodsCheckDetailRecord.setSeverity(severity);
        }
        if(!StringUtils.isEmpty(dangerGoodsCheckDetailRecordDto.getReasonCategoryId())){
            Category reasonCategory = new Category();
            reasonCategory.setId(dangerGoodsCheckDetailRecordDto.getReasonCategoryId());
            dangerGoodsCheckDetailRecord.setReasonCategory(reasonCategory);
        }
    }
    /**
     * 编辑隐患排查整改记录
     * @param dangerGoodsCheckDetailRecordDto
     * @return
     */
    @PutMapping("/dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecord")
    public JsonResult updateDangerGoodsCheckDetailRecord(DangerGoodsCheckDetailRecordDto dangerGoodsCheckDetailRecordDto){
        DangerGoodsCheckDetailRecord savedDangerGoodsCheckDetailRecord = dangerGoodsCheckDetailRecordService.queryObjById(dangerGoodsCheckDetailRecordDto.getId());
        setProperties(savedDangerGoodsCheckDetailRecord,dangerGoodsCheckDetailRecordDto);
        dangerGoodsCheckDetailRecordService.updateObj(savedDangerGoodsCheckDetailRecord);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除隐患排查整改记录
     * @param id
     * @return
     */
    @DeleteMapping("/dangerGoodsCheckDetailRecord/dangerGoodsCheckDetailRecord/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        dangerGoodsCheckDetailRecordService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
