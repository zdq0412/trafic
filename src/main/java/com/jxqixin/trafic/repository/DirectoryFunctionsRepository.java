package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DirectoryFunctions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface DirectoryFunctionsRepository<ID extends Serializable> extends CommonRepository<DirectoryFunctions,ID> {
    /**
     * 根据权限ID删除记录
     * @param id
     */
    @Modifying
    @Query(nativeQuery = true,value="delete from directory_functions where function_id=?1")
    void deleteByFunctionId(String id);

    /**
     * 根据目录ID删除记录
     * @param id 目录ID
     */
    @Modifying
    @Query(nativeQuery = true,value="delete from directory_functions where directory_id=?1")
    void deleteByDirectoryId(String id);
}
