package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.dto.SchemaDto;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.ISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 模式控制器
 */
@RestController
public class SchemaController extends CommonController{
    @Autowired
    private ISchemaService schemaService;
    /**
     * 查询所有模式
     * @return
     */
    @GetMapping("/schema/schemas")
    public JsonResult<List<Schema>> queryAllSchema(){
        List<Schema> list = schemaService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询模式
     * @param nameDto
     * @return
     */
    @GetMapping("/schema/schemasByPage")
    public ModelMap querySchemas(NameDto nameDto){
        Page page = schemaService.findSchemas(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增模式
     * @param schema
     * @return
     */
    @PostMapping("/schema/schema")
    public JsonResult addSchema(Schema schema){
        JsonResult jsonResult = findByName(schema.getName());
        if(jsonResult.getResult().getResultCode()==200){
            schema.setId(UUID.randomUUID().toString());
            schema.setCreateDate(new Date());
            schemaService.addObj(schema);
        }
        return jsonResult;
    }
    /**
     * 编辑模式
     * @param schemaDto
     * @return
     */
    @PutMapping("/schema/schema")
    public JsonResult updateSchema(SchemaDto schemaDto){
        Schema s = schemaService.findByName(schemaDto.getName());

        if(s!=null && !s.getId().equals(schemaDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Schema savedSchema = schemaService.queryObjById(schemaDto.getId());
        savedSchema.setName(schemaDto.getName());
        savedSchema.setNote(schemaDto.getNote());
        savedSchema.setPriority(schemaDto.getPriority());
        schemaService.updateObj(savedSchema);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据模式名称查找
     * @param name
     * @return
     */
    @GetMapping("/schema/schema/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Schema schema = schemaService.findByName(name);
        if(schema==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除模式
     * @param id
     * @return
     */
    @DeleteMapping("/schema/schema/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        try {
            schemaService.deleteById(id);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
