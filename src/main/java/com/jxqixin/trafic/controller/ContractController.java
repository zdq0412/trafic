package com.jxqixin.trafic.controller;

import com.jxqixin.trafic.constant.Result;
import com.jxqixin.trafic.dto.ContractDto;
import com.jxqixin.trafic.model.Employee;
import com.jxqixin.trafic.model.JsonResult;
import com.jxqixin.trafic.model.Contract;
import com.jxqixin.trafic.service.IContractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import sun.java2d.pipe.SpanShapeRenderer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 控制器
 */
@RestController
public class ContractController extends CommonController{
    @Autowired
    private IContractService contractService;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    /**
     * 分页查询
     * @param contractDto
     * @return
     */
    @GetMapping("/contract/contractsByPage")
    public ModelMap queryContracts(ContractDto contractDto){
        Page page = contractService.findContracts(contractDto);
        return pageModelMap(page);
    }
    /**
     * 新增
     * @param contractDto
     * @return
     */
    @PostMapping("/contract/contract")
    public JsonResult addContract(ContractDto contractDto){
        Contract contract = new Contract();
        BeanUtils.copyProperties(contractDto,contract);
        contract.setCreateDate(new Date());
        try {
            contract.setBeginDate(format.parse(contractDto.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            contract.setBeginDate(null);
        }
        try {
            contract.setEndDate(format.parse(contractDto.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            contract.setEndDate(null);
        }
        if(!StringUtils.isEmpty(contractDto.getEmpId())){
            Employee employee = new Employee();
            employee.setId(contractDto.getEmpId());

            contract.setEmployee(employee);
        }
        contractService.addObj(contract);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 编辑
     * @param contractDto
     * @return
     */
    @PutMapping("/contract/contract")
    public JsonResult updateContract(ContractDto contractDto){
        Contract savedContract = contractService.queryObjById(contractDto.getId());
        savedContract.setName(contractDto.getName());
        savedContract.setNote(contractDto.getNote());
        try {
            savedContract.setBeginDate(format.parse(contractDto.getBeginDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            savedContract.setBeginDate(null);
        }

        try {
            savedContract.setEndDate(format.parse(contractDto.getEndDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            savedContract.setEndDate(null);
        }
        contractService.updateObj(savedContract);
        return new JsonResult(Result.SUCCESS);
    }
    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/contract/contract/{id}")
    public JsonResult deleteById(@PathVariable(name="id") String id){
        contractService.deleteById(id);
        return new JsonResult(Result.SUCCESS);
    }
}
