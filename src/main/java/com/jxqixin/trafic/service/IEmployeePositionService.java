package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.EmployeePosition;
import com.jxqixin.trafic.model.Position;

import java.util.List;

public interface IEmployeePositionService extends ICommonService<EmployeePosition> {
    /**
     * 根据人员ID查找员工职位ID
     * @param employeeId
     * @return
     */
    List<String> findIdsByEmployeeId(String employeeId);

    /**
     * 根据人员ID删除记录
     * @param id
     */
    void deleteByEmployeeId(String id);
}
