package com.jxqixin.trafic.repository;

import com.twostep.resume.model.RolePower;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
public interface RolePowerRepository<ID extends Serializable> extends CommonRepository<RolePower,ID> {
    @Query(value = "select * from Role_Power rp where rp.role_id=?1 and power_id=?2",nativeQuery = true)
    RolePower findByRoleIdAndPowerUrl(String id, String powerUrl);
    @Modifying
    @Query(value = "insert into role_power(role_id,power_id)values(?1,?2)",nativeQuery = true)
    void insert(String id, String powerUrl);
    @Modifying
    @Query(value = "delete from role_power where role_id=?1",nativeQuery = true)
    void deleteByRoleId(String roleId);
}
