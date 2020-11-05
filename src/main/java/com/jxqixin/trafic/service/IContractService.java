package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.ContractDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Contract;
import org.springframework.data.domain.Page;

public interface IContractService extends ICommonService<Contract> {
    /**
     * 分页查询信息
     * @param contractDto
     * @return
     */
    Page findContracts(ContractDto contractDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 新增合同
     * @param contract
     */
    void addContract(Contract contract);
}
