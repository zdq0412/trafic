package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Directory;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface DirectoryRepository<ID extends Serializable> extends CommonRepository<Directory,ID> {
    @Query(nativeQuery = true,value="select d.* from directory d inner join t_schema s" +
            " on d.schema_id=s.id where s.selected=1 and (d.status='0' or d.status is null) order by d.priority desc")
    List<Directory> findCurrentDirectorys();
}
