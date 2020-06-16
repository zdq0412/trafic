package com.jxqixin.trafic.repository;
import com.twostep.resume.model.Role;
import java.io.Serializable;
public interface RoleRepository<ID extends Serializable> extends CommonRepository<Role,ID> {
    /**
     * 根据角色名称查找角色
     * @param rolename
     * @return
     */
    Role findByRoleName(String rolename);
}
