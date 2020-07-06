package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.DirectoryFunctions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

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
    @Query(nativeQuery = true,value="select df.function_id from directory_functions df where df.directory_id=?1")
    List<String> findIdsByDirectoryId(String dirId);
}
