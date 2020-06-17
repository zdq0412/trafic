package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Functions;
import org.springframework.data.jpa.repository.Query;
import java.io.Serializable;
import java.util.List;
public interface FunctionsRepository<ID extends Serializable> extends CommonRepository<Functions,ID> {
    @Query("select p from Functions p where p.parent is null")
    List<Functions> queryTopFunctions();
    @Query("select p from Functions p where p.parent.id=?1")
    List<Functions> findByParentId(String id);
    @Query("select rp.functions from RoleFunctions rp where rp.role.name=?1")
    List<Functions> queryByRoleName(String roleName);
}
