package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.CategoryDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Category;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Resume;
import com.jxqixin.trafic.service.ICategoryService;
import com.jxqixin.trafic.util.ExcelUtil;
import com.jxqixin.trafic.util.ImportCategoryUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 类别控制器
 */
@RestController
public class CategoryController extends CommonController{
    @Autowired
    private ICategoryService categoryService;
    /**
     * 查询所有类别
     * @return
     */
    @GetMapping("/category/categorys")
    public JsonResult<List<Category>> queryAllCategory(String type){
        List<Category> list = categoryService.findAll(type);
        return new JsonResult<>(Result.SUCCESS,list);
    }
    /**
     * 分页查询类别
     * @param nameDto
     * @return
     */
    @GetMapping("/category/categorysByPage")
    public ModelMap queryCategorys(NameDto nameDto){
        Page page = categoryService.findCategorys(nameDto);
        return pageModelMap(page);
    }
    /**
     * 新增类别
     * @param categoryDto
     * @return
     */
    @PostMapping("/category/category")
    public JsonResult addCategory(CategoryDto categoryDto){
        //JsonResult jsonResult = findByName(categoryDto.getName());
        Category category = new Category();
        BeanUtils.copyProperties(categoryDto,category);
       // if(jsonResult.getResult().getResultCode()==200){
            //category.setId(UUID.randomUUID().toString());
            category.setCreateDate(new Date());
            if(!StringUtils.isEmpty(categoryDto.getParentId())){
                Category parent = new Category();
                parent.setId(categoryDto.getParentId());

                category.setParent(parent);
            }
            categoryService.addObj(category);
       // }
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑类别
     * @param categoryDto
     * @return
     */
    @PutMapping("/category/category")
    public JsonResult updateCategory(CategoryDto categoryDto){
       /* Category s = categoryService.findByName(categoryDto.getName());

        if(s!=null && !s.getId().equals(categoryDto.getId())){
            return new JsonResult(Result.FAIL);
        }*/
        Category savedCategory = categoryService.queryObjById(categoryDto.getId());
        savedCategory.setName(categoryDto.getName());
        savedCategory.setType(categoryDto.getType());
        savedCategory.setNote(categoryDto.getNote());
        categoryService.updateObj(savedCategory);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据类别名称查找
     * @param name
     * @return
     */
   /* @GetMapping("/category/category/{name}")
    public JsonResult findByName(@PathVariable(name="name") String name){
        Category category = categoryService.findByName(name);
        if(category==null){
            return new JsonResult(Result.SUCCESS);
        }else{
            return new JsonResult(Result.FAIL);
        }
    }*/
    /**
     * 修改类别的状态，删除或正常
     * @param categoryDto
     * @return
     */
    @PostMapping("/category/categoryStatus")
    public JsonResult categoryStatus(CategoryDto categoryDto){
        try {
            categoryService.categoryStatus(categoryDto);
        }catch (RuntimeException ex){
            Result result = Result.FAIL;
            result.setMessage(ex.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 导入类别
     * @return
     */
    @PostMapping("/category/importCategory")
    public JsonResult importCategory(@RequestParam("file") MultipartFile file){
        ExcelUtil excelUtil = new ImportCategoryUtil();
        List<Category> list = (List<Category>) excelUtil.getData(file);
        try{
            categoryService.importCategory(list);
        }catch (RuntimeException e){
            Result result = Result.FAIL;
            result.setMessage(e.getMessage());
            return new JsonResult(result);
        }
        return new JsonResult(Result.SUCCESS);
    }
}
