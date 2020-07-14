package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Position;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface PositionRepository<ID extends Serializable> extends CommonRepository<Position,ID> {
    /**
     * 根据部门ID删除职位
     * @param departmentId
     */
    @Modifying
    @Query(nativeQuery = true,value="delete from m002_position where department_id=?1")
    void deleteByDepartmentId(String departmentId);
    @Query("select p from Position p where p.department.id=?1")
    List<Position> findByDepartmentId(String departmentId);
}
