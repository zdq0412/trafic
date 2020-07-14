package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Department;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface DepartmentRepository<ID extends Serializable> extends CommonRepository<Department,ID> {
   @Query(nativeQuery = true,value = "select d.* from m002_department d" +
           " inner join Org o on o.id=d.org_id where d.name =?1 and o.id=?2")
    Department findByName(String name,String orgId);

    /**
     * 查找根部门
     * @param orgId
     * @return
     */
    @Query(nativeQuery = true,value = "select d.* from m002_department d " +
            " inner join org o on d.org_id=o.id " +
            " where o.id=?1 and d.pid is null ")
    List<Department> findRoots(String orgId);

    /**
     * 根据父部门ID查找子部门
     * @param pid 父部门ID
     * @return
     */
    @Query(nativeQuery = true,value = "select d.* from m002_department d " +
            " inner join m002_department o on d.pid=o.id " +
            " where o.id=?1 ")
    List<Department> findByParentId(String pid);
}
