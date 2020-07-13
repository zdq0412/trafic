package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.PositionDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Position;
import com.jxqixin.trafic.service.IPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
/**
 * 职位控制器
 */
@RestController
public class PositionController extends CommonController{
    @Autowired
    private IPositionService positionService;
    /**
     * 分页查询职位
     * @param nameDto
     * @return
     */
    @GetMapping("/position/positionsByPage")
    public ModelMap queryPositions(NameDto nameDto){
        Page page = positionService.findPositions(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增职位
     * @param position
     * @return
     */
    @PostMapping("/position/position")
    public JsonResult addPosition(Position position){
        position.setId(UUID.randomUUID().toString());
        positionService.addObj(position);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑职位
     * @param positionDto
     * @return
     */
    @PutMapping("/position/position")
    public JsonResult updatePosition(PositionDto positionDto){
        Position savedPosition = positionService.queryObjById(positionDto.getId());
        positionService.updateObj(savedPosition);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除职位
     * @param id
     * @return
     */
    @DeleteMapping("/position/position/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            positionService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
