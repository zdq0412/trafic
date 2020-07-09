package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Employee;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface EmployeeRepository<ID extends Serializable> extends CommonRepository<Employee,ID> {
    /***
     * 根据手机号查找人员信息
     * @return
     */
    Employee findByTel(String tel);
    /**
     * 根据身份证号查找人员信息
     * @param idnum
     * @return
     */
    Employee findByIdnum(String idnum);
    /**
     * 将企业信息设置为空
     * @param id
     */
    @Modifying
    @Query(nativeQuery = true,value = "update m003_employee set org_id=null where id=?1")
    void updateOrg2Null(String id);
}
