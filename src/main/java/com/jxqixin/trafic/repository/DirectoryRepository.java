package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Directory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface DirectoryRepository<ID extends Serializable> extends CommonRepository<Directory,ID> {
    @Query(nativeQuery = true,value="select d.* from directory d inner join t_schema s" +
            " on d.schema_id=s.id where s.selected=1 and (d.status='0' or d.status is null) order by d.priority ")
    List<Directory> findCurrentDirectorys();
    /**
     * 将目录的模式设置为空
     * @param id 模式ID
     */
    @Modifying
    @Query(nativeQuery = true,value = "update directory set schema_id=null where schema_id=?1")
    void deleteSchemaBySchemaId(String id);
    /**
     * 根据名称查找目录
     * @param name
     * @return
     */
    Directory findByName(String name);
}
