package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.TrainingDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Training;
import com.jxqixin.trafic.model.User;
import com.jxqixin.trafic.service.ITrainingService;
import com.jxqixin.trafic.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 培训控制器
 */
@RestController
public class TrainingController extends CommonController{
    @Autowired
    private ITrainingService trainingService;
    @Autowired
    private IUserService userService;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 分页查询培训
     * @return
     */
    @GetMapping("/training/trainingsByPage")
    public ModelMap queryTrainings(TrainingDto trainingDto,HttpServletRequest request){
        Page page = trainingService.findTrainings(trainingDto,getOrg(request));
        return pageModelMap(page);
    }

    /**
     * 引入模板
     * @param templateId
     * @return
     */
    @PostMapping("/training/template")
    public JsonResult importTemplate(String templateId, HttpServletRequest request){
        Training training = trainingService.importTemplate(templateId,getOrg(request),getCurrentUsername(request));
        return new JsonResult(Result.SUCCESS,training);
    }
    /**
     * 新增培训
     * @param trainingDto
     * @return
     */
    @PostMapping("/training/training")
    public JsonResult addTraining(TrainingDto trainingDto,HttpServletRequest request){
        User user = userService.queryUserByUsername(getCurrentUsername(request));
        Training savedTraining = new Training();
        BeanUtils.copyProperties(trainingDto,savedTraining);
        if(!StringUtils.isEmpty(trainingDto.getTrainingDate())){
            try {
                savedTraining.setTrainingDate(format.parse(trainingDto.getTrainingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTraining.setTrainingDate(new Date());
            }
        }
        savedTraining.setRecorder(user.getRealname());
        savedTraining.setCreateDate(new Date());
        savedTraining.setOrg(getOrg(request ));
        savedTraining.setCreator(getCurrentUsername(request));
        trainingService.addObj(savedTraining);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑培训
     * @param trainingDto
     * @return
     */
    @PutMapping("/training/training")
    public JsonResult updateTraining(TrainingDto trainingDto){
        Training savedTraining = trainingService.queryObjById(trainingDto.getId());
        if(!StringUtils.isEmpty(trainingDto.getTrainingDate())){
            try {
                savedTraining.setTrainingDate(format.parse(trainingDto.getTrainingDate()));
            } catch (ParseException e) {
                e.printStackTrace();
                savedTraining.setTrainingDate(new Date());
            }
        }
        savedTraining.setName(trainingDto.getName());
        savedTraining.setNote(trainingDto.getNote());
        trainingService.updateObj(savedTraining);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 修改培训内容
     * @param trainingDto
     * @return
     */
    @PostMapping("/training/content")
    public JsonResult updateContent(TrainingDto trainingDto){
        Training savedTraining = trainingService.queryObjById(trainingDto.getId());
        savedTraining.setContent(trainingDto.getContent());
        savedTraining.setTrainingName(trainingDto.getTrainingName());
        savedTraining.setTrainingPlace(trainingDto.getTrainingPlace());
        savedTraining.setAttendance(trainingDto.getAttendance());
        savedTraining.setAttendants(trainingDto.getAttendants());
        savedTraining.setPresident(trainingDto.getPresident());
        savedTraining.setRecorder(trainingDto.getRecorder());
        savedTraining.setRealAttendance(trainingDto.getRealAttendance());
        trainingService.updateObj(savedTraining);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除培训
     * @param id
     * @return
     */
    @DeleteMapping("/training/training/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        trainingService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
