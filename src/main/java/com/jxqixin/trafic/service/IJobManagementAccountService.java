package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.JobManagementAccountDto;
import com.jxqixin.trafic.model.JobManagementAccount;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;
public interface IJobManagementAccountService extends ICommonService<JobManagementAccount> {
    /**
     * 分页查询信息
     * @param JobManagementDto
     * @return
     */
    Page findJobManagementAccounts(JobManagementAccountDto JobManagementDto, Org org);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
