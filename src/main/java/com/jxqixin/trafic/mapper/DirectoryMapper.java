package com.jxqixin.trafic.mapper;
import com.jxqixin.trafic.model.Directory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
@Mapper
public interface DirectoryMapper {
    @Select("select d.id,d.name,d.icon,d.c_index from Directory d inner join T_Schema s " +
            " where d.schema_id=s.id and s.selected=1 order by d.priority desc")
    List<Directory> findDirectoies();
}
