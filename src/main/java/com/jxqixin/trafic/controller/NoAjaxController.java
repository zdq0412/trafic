package com.jxqixin.trafic.controller;
import com.twostep.resume.model.*;
import com.twostep.resume.model.Dictionary;
import com.twostep.resume.service.*;
import com.twostep.utils.ExcelUtil;
import com.twostep.utils.ResumeExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 非ajax跳转
 */
@Controller
public class NoAjaxController {
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Autowired
    private IResumeService resumeService;
    @Autowired
    private IEducationExperienceService educationExperienceService;
    @Autowired
    private IWorkExperienceService workExperienceService;
    @Autowired
    private IProjectExperienceService projectExperienceService;
    @Autowired
    private IPowerService powerService;
    @Autowired
    private IDictionaryService dictionaryService;
    @RequestMapping("console")
    public String toConsole(){
        return "resume/console";
    }
    @RequestMapping("resume")
    public String resume(){
        return "resume/resume";
    }
    @RequestMapping("addCallRecord.html")
    public String addCallRecord(){
        return "resume/addCallRecord";
    }
    @RequestMapping("addJobInterview.html")
    public String addJobInterview(){
        return "resume/addJobInterview";
    }
    @RequestMapping("callRecords.html")
    public String callRecords(){
        return "resume/callRecords";
    }
    @RequestMapping("myCallRecords.html")
    public String myCallRecords(){
        return "resume/myCallRecords";
    }
    @RequestMapping("jobInterview.html")
    public String jobInterview(){
        return "resume/jobInterview";
    }
    @RequestMapping("dictionaryChild.html")
    public String dictionaryChild(){
        return "resume/dictionaryChild";
    }
    @RequestMapping("myJobInterview.html")
    public String myJobInterview(){
        return "resume/myJobInterview";
    }
    @RequestMapping("myResume")
    public String myResume(){
        return "resume/myResume";
    }
    @RequestMapping("index.html")
    public String toIndex(){
        return "index";
    }
    @RequestMapping("showUsers.html")
    public String showUsers(){
        return "resume/showUsers";
    }
    @RequestMapping("/")
    public String index(){
        return "index";
    }
    @RequestMapping("modifyPassword")
    public String toModifyPassword(){
        return "resume/modifyPassword";
    }
    @RequestMapping("myReport")
    public String myReport(){
        return "resume/myReport";
    }
    @RequestMapping("sysReport")
    public String sysReport(){
        return "resume/sysReport";
    }
    @RequestMapping("freeze")
    public String freeze(){
        return "resume/freeze";
    }
    @RequestMapping("received")
    public String received(){
        return "resume/received";
    }
    @RequestMapping("dictionary")
    public String dictionary(){
        return "resume/dictionary";
    }
    @RequestMapping("createResume")
    public String toCreateResume(){
        return "resume/createResume";
    }
    @RequestMapping("showPowers.html")
    public String showPowers(String roleName,HttpServletRequest request){
        List<Power> firstLevelList = powerService.queryTopPower();
        //根据角色名称查找权限
        List<Power> rolePowers = powerService.queryByRoleName(roleName);
        List<TreeNode> treeNodes = new ArrayList<>();
        for(Power ic:firstLevelList){
            TreeNode treeNode = new TreeNode();
            treeNode.setId(ic.getUrl());
            treeNode.setTitle(ic.getPowerName());
            treeNode.setParentId(ic.getParent()==null?"":ic.getParent().getUrl());
            buildChilren(treeNode,rolePowers);
            treeNodes.add(treeNode);
        }
      request.setAttribute("data",treeNodes);
        return "resume/showPowers";
    }
    /**
     * 构建子节点
     * @param treeNode
     */
    private void buildChilren(TreeNode treeNode,List<Power> myPowerList) {
        checkTreeNode(treeNode,myPowerList);
        List<Power> inventoryClassList = powerService.findByParentUrl(treeNode.getId());
        List<TreeNode> chilrenTreeNode = new ArrayList<>();
        if(CollectionUtils.isEmpty(inventoryClassList)){
            treeNode.setChildren(chilrenTreeNode);
            return ;
        }else{
            for(Power inventoryClass : inventoryClassList){
                TreeNode childrenNode = new TreeNode();
                childrenNode.setId(inventoryClass.getUrl());
                checkTreeNode(childrenNode,myPowerList);
                childrenNode.setParentId(inventoryClass.getParent().getUrl());
                childrenNode.setTitle(inventoryClass.getPowerName());
                buildChilren(childrenNode,myPowerList);
                chilrenTreeNode.add(childrenNode);
            }
            treeNode.setChildren(chilrenTreeNode);
        }
    }
    /**
     * 选中节点
     */
    private void checkTreeNode(TreeNode treeNode,List<Power> powers){
        if(treeNode!=null &&!CollectionUtils.isEmpty(powers)){
            for(Power power : powers){
                if(power.getUrl().equals(treeNode.getId())){
                    treeNode.setCheckArr("1");
                }
            }
        }
    }
    @RequestMapping("user")
    public String toUser(HttpServletRequest request){
        return "resume/user";
    }
    @RequestMapping("role")
    public String toRole(HttpServletRequest request){
        return "resume/role";
    }
    @RequestMapping("editResume")
    public String toEditResume(String id, HttpServletRequest request){
        Resume resume = resumeService.queryObjById(id);
        List<EducationExperience> educationExperienceList = educationExperienceService.findByResumeId(id);
        List<WorkExperience> workExperienceList = workExperienceService.findByResumeId(id);
        List<ProjectExperience> projectExperienceList = projectExperienceService.findByResumeId(id);
        request.setAttribute("resume",resume);
        request.setAttribute("educationExperienceList",educationExperienceList);
        request.setAttribute("workExperienceList",workExperienceList);
        request.setAttribute("projectExperienceList",projectExperienceList);

        List<Dictionary> positionList = dictionaryService.findByCategory("职位名称");
        List<Dictionary> cityList = dictionaryService.findByCategory("城市名称");
        request.setAttribute("positionList",positionList);
        request.setAttribute("cityList",cityList);
        return "resume/editResume";
    }

    /**
     * 导出数据
     */
    @RequestMapping("exportAll")
    public void exportAll(HttpServletResponse response){
        List<Resume> resumeList=resumeService.findAll();
        //设置Excel中的title
        String[] titles = new String[ResumeExcelUtil.cols.size()];
        ResumeExcelUtil.cols.toArray(titles);
        //存储所有数据
        List<String[]> dataList = new ArrayList<>();
        for(Resume  resume: resumeList) {
            String[] data = {
                    resume.getName(),
                    resume.getSex(),
                    resume.getTel(),
                    resume.getMarriage(),
                    resume.getBirthday()==null?"":format.format(resume.getBirthday()),
                    resume.getEmail(),
                    resume.getEducation(),
                    resume.getSchool(),
                    resume.getMajor(),
                    resume.getCity(),
                    resume.getPosition(),
                    resume.getCompany(),
                    resume.getCurrentState(),
                    resume.getSalary(),
                    resume.getExpectCity(),
                    resume.getExpectSalary(),
                    resume.getExpectPosition(),
                    resume.getExpectIndustry()
            };
            dataList.add(data);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        HSSFWorkbook workBook = ExcelUtil.getHSSFWorkbook("简历", titles, dataList);
        try {
            this.setResponseHeader(response, "简历"+sdf.format(new Date())+".xls");
            OutputStream os = response.getOutputStream();
            workBook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //发送响应流方法
    public void setResponseHeader(HttpServletResponse response, String fileName) {
        try {
            try {
                fileName = new String(fileName.getBytes(),"ISO8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            response.setContentType("application/octet-stream;charset=ISO8859-1");
            response.setHeader("Content-Disposition", "attachment;filename="+ fileName);
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
