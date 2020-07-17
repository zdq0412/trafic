package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.EmployeeDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.OrgDocDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.OrgDoc;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.IOrgDocService;
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
import java.util.List;
import java.util.UUID;
/**
 * 企业资质文件控制器
 */
@RestController
public class OrgDocController extends CommonController{
    @Autowired
    private IOrgDocService orgDocService;
    @Autowired
    private IUserService userService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 查询所有企业资质文件
     * @return
     */
    @GetMapping("/orgDoc/orgDocs")
    public JsonResult<List<OrgDoc>> queryAllOrgDoc(){
        List<OrgDoc> list = orgDocService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询企业资质文件
     * @param nameDto
     * @return
     */
    @GetMapping("/orgDoc/orgDocsByPage")
    public ModelMap queryOrgDocs(NameDto nameDto){
        Page page = orgDocService.findOrgDocs(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增企业资质
     * @param orgDocDto
     * @return
     */
    @PostMapping("/orgDoc/addOrgDoc")
    public JsonResult addOrgDoc(OrgDocDto orgDocDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "photo";
            File savedFile = upload(dir,file);
            if(savedFile!=null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
           OrgDoc orgDoc = new OrgDoc();
            buildOrgDoc(orgDocDto,orgDoc);
            orgDoc.setUploadDate(new Date());
            if(!StringUtils.isEmpty(orgDocDto.getBeginDate())){
                try {
                    orgDoc.setBeginDate(format.parse(orgDocDto.getBeginDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if(!StringUtils.isEmpty(orgDocDto.getEndDate())){
                try {
                    orgDoc.setEndDate(format.parse(orgDocDto.getEndDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            orgDoc.setRealPath(savedFile.getAbsolutePath());
            orgDoc.setUrl(urlMapping);
            orgDoc.setOrg(user.getOrg());

            orgDocService.addObj(orgDoc);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result,urlMapping);
    }
    /**
     * 编辑企业资质
     * @param orgDocDto
     * @return
     */
    @PostMapping("/orgDoc/updateOrgDoc")
    public JsonResult updateEmployee(OrgDocDto orgDocDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg()==null?"":(user.getOrg().getName()+"/")) + "orgDoc";
            File savedFile = upload(dir,file);
            if(savedFile!=null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            OrgDoc orgDoc = orgDocService.queryObjById(orgDocDto.getId());
            buildOrgDoc(orgDocDto,orgDoc);
            orgDoc.setUrl(urlMapping);
            orgDoc.setRealPath(savedFile.getAbsolutePath());
            orgDocService.updateObj(orgDoc);
        } catch (RuntimeException | IOException e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }

    private void buildOrgDoc(OrgDocDto orgDocDto,OrgDoc orgDoc){
        orgDoc.setName(orgDocDto.getName());
        if(!StringUtils.isEmpty(orgDocDto.getBeginDate())){
            try {
                orgDoc.setBeginDate(format.parse(orgDocDto.getBeginDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if(!StringUtils.isEmpty(orgDocDto.getEndDate())){
            try {
                orgDoc.setEndDate(format.parse(orgDocDto.getEndDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        orgDoc.setDocNum(orgDocDto.getDocNum());
    }
    /**
     * 编辑企业资质,不修改照片
     * @param orgDocDto
     * @return
     */
    @PostMapping("/orgDoc/updateOrgDocNoPhoto")
    public JsonResult updateEmployeeNoPhoto(OrgDocDto orgDocDto, HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Result result = Result.SUCCESS;
        try {
            OrgDoc orgDoc = orgDocService.queryObjById(orgDocDto.getId());
            buildOrgDoc(orgDocDto,orgDoc);
            orgDocService.updateObj(orgDoc);
        } catch (RuntimeException  e) {
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        return new JsonResult(result);
    }
    /**
     * 根据ID删除企业资质文件
     * @param id
     * @return
     */
    @DeleteMapping("/orgDoc/orgDoc/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
            orgDocService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
