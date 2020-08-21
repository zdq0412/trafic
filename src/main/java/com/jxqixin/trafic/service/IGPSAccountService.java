package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.GPSAccountDto;
import com.jxqixin.trafic.model.GPSAccount;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;
public interface IGPSAccountService extends ICommonService<GPSAccount> {
    /**
     * 分页查询
     * @param gpsAccountDto
     * @return
     */
    Page findGPSAccounts(GPSAccountDto gpsAccountDto, Org org);
    /**
     * 根据ID删除模式，同时删除模式下的所有目录
     */
    void deleteById(String id);
}
