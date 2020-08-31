package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.JobManagementAccountDto;
import com.jxqixin.trafic.dto.JobManagementAccountDto;
import com.jxqixin.trafic.model.*;
import com.jxqixin.trafic.service.IJobManagementAccountService;
import com.jxqixin.trafic.service.IUserService;
import com.jxqixin.trafic.util.FileUtil;
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
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 作业管理控制器
 */
@RestController
public class JobManagementAccountController extends CommonController{
    @Autowired
    private IJobManagementAccountService jobManagementAccountService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param jobManagementAccountDto
     * @return
     */
    @GetMapping("/jobManagementAccount/jobManagementAccountsByPage")
    public ModelMap queryJobManagementAccounts(JobManagementAccountDto jobManagementAccountDto,HttpServletRequest request){
        Page page = jobManagementAccountService.findJobManagementAccounts(jobManagementAccountDto,getOrg(request));
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param jobManagementAccountDto
     * @return
     */
    @PostMapping("/jobManagementAccount/jobManagementAccount")
    public JsonResult addJobManagementAccount(JobManagementAccountDto jobManagementAccountDto,HttpServletRequest request, @RequestParam("file") MultipartFile file){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        JobManagementAccount savedJobManagementAccount = new JobManagementAccount();
        BeanUtils.copyProperties(jobManagementAccountDto,savedJobManagementAccount);
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "jobManagementAccount";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            savedJobManagementAccount.setUrl(urlMapping);
            savedJobManagementAccount.setRealPath(savedFile.getAbsolutePath());
            savedJobManagementAccount.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        if(!StringUtils.isEmpty(jobManagementAccountDto.getJobManagementAccountTypeId())){
            Category category = new Category();
            category.setId(jobManagementAccountDto.getJobManagementAccountTypeId());

            savedJobManagementAccount.setJobManagementAccountType(category);
        }
        savedJobManagementAccount.setCreateDate(new Date());
        savedJobManagementAccount.setOrg(getOrg(request));
        savedJobManagementAccount.setCreator(getCurrentUsername(request));
        jobManagementAccountService.addObj(savedJobManagementAccount);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param jobManagementAccountDto
     * @return
     */
    @PostMapping("/jobManagementAccount/updateJobManagementAccount")
    public JsonResult updateJobManagementAccount(JobManagementAccountDto jobManagementAccountDto, @RequestParam("file") MultipartFile file, HttpServletRequest request){
        JobManagementAccount savedJobManagementAccount = jobManagementAccountService.queryObjById(jobManagementAccountDto.getId());
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        String urlMapping = "";
        Result result = Result.SUCCESS;
        try {
            String dir = (user.getOrg() == null ? "" : (user.getOrg().getName() + "/")) + "jobManagementAccount";
            File savedFile = upload(dir, file);
            if (savedFile != null) {
                urlMapping = getUrlMapping().substring(1).replace("*", "") + dir + "/" + savedFile.getName();
            }
            if(!StringUtils.isEmpty(savedJobManagementAccount.getRealPath())){
                FileUtil.deleteFile(savedJobManagementAccount.getRealPath());
            }
            if(!StringUtils.isEmpty(jobManagementAccountDto.getJobManagementAccountTypeId())){
                Category category = new Category();
                category.setId(jobManagementAccountDto.getJobManagementAccountTypeId());

                savedJobManagementAccount.setJobManagementAccountType(category);
            }
            savedJobManagementAccount.setUrl(urlMapping);
            savedJobManagementAccount.setRealPath(savedFile.getAbsolutePath());
            savedJobManagementAccount.setFilename(file.getOriginalFilename());
        }catch (RuntimeException | IOException e){
            e.printStackTrace();
            result = Result.FAIL;
            result.setMessage(e.getMessage());
        }
        savedJobManagementAccount.setName(jobManagementAccountDto.getName());
        savedJobManagementAccount.setNote(jobManagementAccountDto.getNote());
        jobManagementAccountService.updateObj(savedJobManagementAccount);
        return new JsonResult(result);
    }
    /**
     * 编辑
     * @param jobManagementAccountDto
     * @return
     */
    @PostMapping("/jobManagementAccount/updateJobManagementAccountNoFile")
    public JsonResult updateJobManagementAccountNoFile(JobManagementAccountDto jobManagementAccountDto, HttpServletRequest request){
        JobManagementAccount savedJobManagementAccount = jobManagementAccountService.queryObjById(jobManagementAccountDto.getId());
        Result result = Result.SUCCESS;
        savedJobManagementAccount.setName(jobManagementAccountDto.getName());
        savedJobManagementAccount.setNote(jobManagementAccountDto.getNote());
        if(!StringUtils.isEmpty(jobManagementAccountDto.getJobManagementAccountTypeId())){
            Category category = new Category();
            category.setId(jobManagementAccountDto.getJobManagementAccountTypeId());

            savedJobManagementAccount.setJobManagementAccountType(category);
        }
        jobManagementAccountService.updateObj(savedJobManagementAccount);
        return new JsonResult(result);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/jobManagementAccount/jobManagementAccount/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        jobManagementAccountService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
