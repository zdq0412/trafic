package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.ResumeDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.service.IResumeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 控制器
 */
@RestController
public class ResumeController extends CommonController{
    @Autowired
    private IResumeService resumeService;
    /**
     * 分页查询
     * @param resumeDto
     * @return
     */
    @GetMapping("/resume/resumesByPage")
    public ModelMap queryResumes(ResumeDto resumeDto){
        Page page = resumeService.findResumes(resumeDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param resumeDto
     * @return
     */
    @PostMapping("/resume/resume")
    public JsonResult addResume(ResumeDto resumeDto){
        Resume resume = new Resume();
        BeanUtils.copyProperties(resumeDto,resume);
        resume.setCreateDate(new Date());
        if(!StringUtils.isEmpty(resumeDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(resumeDto.getEmpId());

            resume.setEmployee(employee);
        }
        resumeService.addObj(resume);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param resumeDto
     * @return
     */
    @PutMapping("/resume/resume")
    public JsonResult updateResume(ResumeDto resumeDto){
        Resume savedResume = resumeService.queryObjById(resumeDto.getId());
        savedResume.setName(resumeDto.getName());
        savedResume.setNote(resumeDto.getNote());
        resumeService.updateObj(savedResume);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/resume/resume/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        resumeService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
