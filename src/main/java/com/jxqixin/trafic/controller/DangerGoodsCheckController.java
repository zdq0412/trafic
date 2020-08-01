package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DangerGoodsCheckDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IDangerGoodsCheckService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 危险货物运输企业安全生产隐患排查整改台账控制器
 */
@RestController
public class DangerGoodsCheckController extends CommonController{
    @Autowired
    private IDangerGoodsCheckService dangerGoodsCheckService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查危险货物隐患排查
     * @param nameDto
     * @return
     */
    @GetMapping("/dangerGoodsCheck/dangerGoodsChecksByPage")
    public ModelMap queryDangerGoodsChecks(NameDto nameDto){
        Page page = dangerGoodsCheckService.findDangerGoodsChecks(nameDto);
        return pageModelMap(page);
    }

    /**
     * 新增危险货物隐患
     * @param dangerGoodsCheckDto
     * @return
     */
    @PostMapping("/dangerGoodsCheck/dangerGoodsCheck")
    public JsonResult addDangerGoodsCheck(DangerGoodsCheckDto dangerGoodsCheckDto,HttpServletRequest request){
        DangerGoodsCheck savedDangerGoodsCheck = new DangerGoodsCheck();
        BeanUtils.copyProperties(dangerGoodsCheckDto,savedDangerGoodsCheck);
        savedDangerGoodsCheck.setCreateDate(new Date());
        savedDangerGoodsCheck.setCreator(getCurrentUsername(request));
        savedDangerGoodsCheck.setOrg(getOrg(request));
        dangerGoodsCheckService.addObj(savedDangerGoodsCheck);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑危险货物隐患
     * @param dangerGoodsCheckDto
     * @return
     */
    @PutMapping("/dangerGoodsCheck/dangerGoodsCheck")
    public JsonResult updateDangerGoodsCheck(DangerGoodsCheckDto dangerGoodsCheckDto){
        DangerGoodsCheck savedDangerGoodsCheck = dangerGoodsCheckService.queryObjById(dangerGoodsCheckDto.getId());
        savedDangerGoodsCheck.setName(dangerGoodsCheckDto.getName());
        savedDangerGoodsCheck.setNote(dangerGoodsCheckDto.getNote());
        dangerGoodsCheckService.updateObj(savedDangerGoodsCheck);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改危险货物隐患内容
     * @param id 危险货物隐患ID
     * @param details 危险货物隐患详情字符串，字段之间以|分割，对象之间以#分割
     * @return
     */
    @PostMapping("/dangerGoodsCheck/content")
    public JsonResult updateContent(String id, String details){
        DangerGoodsCheck template = new DangerGoodsCheck();
        template.setId(id);

        List<DangerGoodsCheckDetailRecord> detailList = new ArrayList<>();
        if(!StringUtils.isEmpty(details)){
            String[] detailObjs = details.split("#");
            if(detailObjs!=null && detailObjs.length>0){
                for(int i = 0;i<detailObjs.length;i++){
                    DangerGoodsCheckDetailRecord detail = new DangerGoodsCheckDetailRecord();
                    detail.setDangerGoodsCheck(template);
                    String[] fields = detailObjs[i].split("\\|");
                    if(fields!=null &&fields.length>0){
                        String checkDate = fields[0];
                        String person = fields[1];
                        String cancelDate = fields[2];
                        String checkedOrg = fields[3];
                        String hiddenDanger = fields[4];
                        String correctiveAction = fields[5];
                        String timelimit = fields[6];
                        String endTime = fields[7];
                        String note = fields[8];
                        detail.setCheckedOrg(checkedOrg);
                        detail.setHiddenDanger(hiddenDanger);
                        detail.setCorrectiveAction(correctiveAction);
                        detail.setTimelimit(timelimit);
                        detail.setDetailNote(note);
                        if(!StringUtils.isEmpty(checkDate)){
                            try {
                                detail.setCheckDate(format.parse(checkDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!StringUtils.isEmpty(cancelDate)){
                            try {
                                detail.setCancelDate(format.parse(cancelDate));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        if(!StringUtils.isEmpty(endTime)){
                            try {
                                detail.setEndTime(format.parse(endTime));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        detail.setPerson(person);

                    }
                    detailList.add(detail);
                }
            }
        }
        dangerGoodsCheckService.updateDetails(id,detailList);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 引入模板
     * @param templateId
     * @return
     */
    @PostMapping("/dangerGoodsCheck/template")
    public JsonResult importTemplate(String templateId, HttpServletRequest request){
        dangerGoodsCheckService.importTemplate(templateId,getOrg(request),getCurrentUsername(request));
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除危险货物隐患
     * @param id
     * @return
     */
    @DeleteMapping("/dangerGoodsCheck/dangerGoodsCheck/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        dangerGoodsCheckService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
