package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.RoleFunctions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.io.Serializable;
public interface RoleFunctionsRepository<ID extends Serializable> extends CommonRepository<RoleFunctions,ID> {
    @Query(value = "select * from Role_Functions rp where rp.role_id=?1 and Functions_id=?2",nativeQuery = true)
    RoleFunctions findByRoleIdAndFunctionsUrl(String id, String FunctionsUrl);
    @Modifying
    @Query(value = "insert into role_Functions(role_id,functions_id)values(?1,?2)",nativeQuery = true)
    void insert(String role_id, String function_id);
    @Modifying
    @Query(value = "delete from role_functions where role_id=?1",nativeQuery = true)
    void deleteByRoleId(String roleId);
}
