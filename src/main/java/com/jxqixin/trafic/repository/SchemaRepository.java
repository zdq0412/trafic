package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Schema;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;

public interface SchemaRepository<ID extends Serializable> extends CommonRepository<Schema,ID> {
    Schema findByName(String name);
    /**
     * 将模式selected字段都设置为false
     */
    @Modifying
    @Query(nativeQuery = true,value="update T_SCHEMA set selected=0")
    void updateSelected2False();
    /**
     *  根据模式ID设置selected为true
     * @param schemaId
     */
    @Modifying
    @Query(nativeQuery = true,value="update T_SCHEMA set selected=1 where id=?1")
    void updateSelected2TrueBySchemaId(String schemaId);
}
