package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.EmployeePosition;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface EmployeePositionRepository<ID extends Serializable> extends CommonRepository<EmployeePosition,ID> {
    @Query(nativeQuery = true,value="select ep.position_id from employee_position ep where ep.employee_id=?1")
    List<String> findIdsByEmployeeId(String employeeId);
    @Modifying
    @Query(nativeQuery = true,value="delete from employee_position where employee_id=?1")
    void deleteByEmployeeId(String employeeId);
}
