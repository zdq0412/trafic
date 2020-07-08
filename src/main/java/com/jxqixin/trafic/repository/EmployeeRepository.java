package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Employee;

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
}
