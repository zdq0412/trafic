package com.jxqixin.trafic.controller;
import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.DirectoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Functions;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Schema;
import com.jxqixin.trafic.service.IDirectoryService;
import com.jxqixin.trafic.service.IFunctionsService;
import com.jxqixin.trafic.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 目录控制器
 */
@RestController
public class DirectoryController extends CommonController{
    @Autowired
    private IDirectoryService directoryService;
    @Autowired
    private IFunctionsService functionsService;
    /**
     * 查询所有目录
     * @return
     */
    @GetMapping("/directory/directorys")
    public JsonResult<List<Directory>> queryAllDirectory(){
        List<Directory> list = directoryService.findAll();
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询目录
     * @param nameDto
     * @return
     */
    @GetMapping("/directory/directorysByPage")
    public ModelMap queryDirectorys(NameDto nameDto){
        Page page = directoryService.findDirectorys(nameDto);
        return pageModelMap(page);
    }

    /**
     * 新增目录
     * @param directoryDto
     * @return
     */
    @PostMapping("/directory/directory")
    public JsonResult addDirectory(DirectoryDto directoryDto){
        JsonResult jsonResult = findByName(directoryDto.getName(),directoryDto.getSchemaId());
        Directory directory = new Directory();
        BeanUtils.copyProperties(directoryDto,directory);
        if(jsonResult.getResult().getResultCode()==200){
            directory.setId(UUID.randomUUID().toString());
            directory.setCreateDate(new Date());
            if(!StringUtils.isEmpty(directoryDto.getSchemaId())){
                Schema schema = new Schema();
                schema.setId(directoryDto.getSchemaId());

                directory.setSchema(schema);
            }
            directory.setIndex(UUID.randomUUID().toString());
            directoryService.addObj(directory);
        }
        return jsonResult;
    }
    /**
     * 编辑目录
     * @param directoryDto
     * @return
     */
    @PutMapping("/directory/directory")
    public JsonResult updateDirectory(DirectoryDto directoryDto){
        Directory s = directoryService.findByName(directoryDto.getName(),directoryDto.getSchemaId());

        if(s!=null && !s.getId().equals(directoryDto.getId())){
            return new JsonResult(Result.FAIL);
        }
        Directory savedDirectory = directoryService.queryObjById(directoryDto.getId());
        savedDirectory.setIcon(directoryDto.getIcon());
        savedDirectory.setNote(directoryDto.getNote());
        savedDirectory.setName(directoryDto.getName());
        if(!StringUtils.isEmpty(directoryDto.getSchemaId())){
            Schema schema = new Schema();
            schema.setId(directoryDto.getSchemaId());

            savedDirectory.setSchema(schema);
        }
        directoryService.updateObj(savedDirectory);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据目录名称查找
     * @param name
     * @return
     */
    @GetMapping("/directory/directory/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name,String schemaId){
        Directory directory = directoryService.findByName(name,schemaId);
        if(directory==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }
    /**
     * 根据ID删除目录
     * @param id
     * @return
     */
    @DeleteMapping("/directory/directory/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        directoryService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
