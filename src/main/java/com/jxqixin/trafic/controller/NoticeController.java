package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NoticeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Notice;
import com.jxqixin.trafic.model.OrgCategory;
import com.jxqixin.trafic.service.INoticeService;
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
import java.util.List;

/**
 * 企业发文通知文件控制器
 */
@RestController
public class NoticeController extends CommonController{
    @Autowired
    private INoticeService noticeService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询企业发文通知文件
     * @param nameDto
     * @return
     */
    @GetMapping("/notice/noticesByPage")
    public ModelMap queryNotices(NameDto nameDto,HttpServletRequest request){
        Page page = noticeService.findNotices(nameDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增企业发文通知文件
     * @param noticeDto
     * @return
     */
    @PostMapping("/notice/notice")
    public JsonResult addNotice(NoticeDto noticeDto, HttpServletRequest request){
        Notice notice = new Notice();
        BeanUtils.copyProperties(noticeDto,notice);

        if(!StringUtils.isEmpty(noticeDto.getPublishDate())){
            try {
                notice.setPublishDate(format.parse(noticeDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(noticeDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(noticeDto.getOrgCategoryId());
            notice.setOrgCategory(orgCategory);
        }
        noticeService.addNotice(notice,getOrg(request));
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 修改企业发文通知内容
     * @param noticeDto
     * @return
     */
    @PutMapping("/notice/content")
    public JsonResult noticeContent(NoticeDto noticeDto){
        Notice notice = noticeService.queryObjById(noticeDto.getId());
        notice.setContent(noticeDto.getContent());
        noticeService.updateObj(notice);
        return new JsonResult(Result.SUCCESS);
    }

    /**
     * 编辑企业发文通知文件
     * @param noticeDto
     * @return
     */
    @PutMapping("/notice/notice")
    public JsonResult updateNotice(NoticeDto noticeDto){
        Notice savedNotice = noticeService.queryObjById(noticeDto.getId());
        savedNotice.setName(noticeDto.getName());
        savedNotice.setNote(noticeDto.getNote());
        if(!StringUtils.isEmpty(noticeDto.getOrgCategoryId())){
            OrgCategory orgCategory = new OrgCategory();
            orgCategory.setId(noticeDto.getOrgCategoryId());
            savedNotice.setOrgCategory(orgCategory);
        }
        if(!StringUtils.isEmpty(noticeDto.getProvinceId())){
            Category province = new Category();
            province.setId(noticeDto.getProvinceId());
            savedNotice.setProvince(province);
        }
        if(!StringUtils.isEmpty(noticeDto.getCityId())){
            Category city = new Category();
            city.setId(noticeDto.getCityId());
            savedNotice.setCity(city);
        }
        if(!StringUtils.isEmpty(noticeDto.getRegionId())){
            Category region = new Category();
            region.setId(noticeDto.getRegionId());
            savedNotice.setRegion(region);
        }
        savedNotice.setPublishDepartment(noticeDto.getPublishDepartment());
        savedNotice.setTimeliness(noticeDto.getTimeliness());

        if(!StringUtils.isEmpty(noticeDto.getPublishDate())){
            try {
                savedNotice.setPublishDate(format.parse(noticeDto.getPublishDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        noticeService.updateObj(savedNotice);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除企业发文通知文件
     * @param id
     * @return
     */
    @DeleteMapping("/notice/notice/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        noticeService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据法律法规ID或安全规章制度ID查找企业通知
     * @param lawOrRulesId
     * @return
     */
    @GetMapping("/notice/notices")
    public List<Notice> findByLawIdOrRulesId(String lawOrRulesId,String type){
        if("law".equals(type)){
            return noticeService.findByLawId(lawOrRulesId);
        }
        if("rules".equals(type)){
            return noticeService.findByRulesId(lawOrRulesId);
        }
        return new ArrayList<>();
    }
}
