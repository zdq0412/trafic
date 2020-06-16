package com.jxqixin.trafic.repository;
import com.twostep.resume.model.Power;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface PowerRepository<ID extends Serializable> extends CommonRepository<Power,ID> {
    @Query("select p from Power p where p.parent is null")
    List<Power> queryTopPower();
    @Query("select p from Power p where p.parent.url=?1")
    List<Power> findByParentUrl(String url);
    @Query("select rp.power from RolePower rp where rp.role.roleName=?1")
    List<Power> queryByRoleName(String roleName);
}
