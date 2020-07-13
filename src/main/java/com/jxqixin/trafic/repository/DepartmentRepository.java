package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Department;

import java.io.Serializable;

public interface DepartmentRepository<ID extends Serializable> extends CommonRepository<Department,ID> {
    Department findByName(String name);
}
