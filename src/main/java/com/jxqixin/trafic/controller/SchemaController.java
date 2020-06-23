package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.ISchemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * 模式控制器
 */
@RestController
@RequestMapping("schema")
public class SchemaController extends CommonController{
    @Autowired
    private ISchemaService schemaService;
    /**
     * 查询所有模式
     * @return
     */
    @GetMapping("/schemas")
    public JsonResult<List<Schema>> queryAllSchema(){
        List<Schema> list = schemaService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
}
