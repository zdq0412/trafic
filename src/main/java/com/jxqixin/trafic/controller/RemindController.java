package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Remind;
import com.jxqixin.trafic.service.IRemindService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 过期提醒控制器
 */
@RestController
public class RemindController extends CommonController {
    @Autowired
    private IRemindService remindService;
    /**
     * 查找所有的提醒记录
     * @return
     */
    @GetMapping("/remind/reminds")
    public List<Remind> queryAllReminds(HttpServletRequest request){
        List<Remind> list = remindService.findAllReminds(getOrg(request));
        return  list;
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/remind/remind/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        Result result = Result.SUCCESS;
        try {
            remindService.deleteRemind(id);
        }catch (RuntimeException e){
           result = Result.FAIL;
            result.setMessage("该问题尚未处理，请处理后删除!");
            return new JsonResult(result);
        }
        return new JsonResult(result);
    }
}
