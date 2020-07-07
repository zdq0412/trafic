package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Employee;

import java.io.Serializable;

public interface EmployeeRepository<ID extends Serializable> extends CommonRepository<Employee,ID> {
}
