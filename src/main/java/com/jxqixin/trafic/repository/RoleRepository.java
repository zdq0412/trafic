package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.Role;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface RoleRepository<ID extends Serializable> extends CommonRepository<Role,ID> {
    /**
     * 根据角色名称查找角色
     * @param rolename
     * @return
     */
    @Query(value = "select r.* from Role r  where r.name=?1 and r.org_id is null",nativeQuery = true)
    Role findByName(String rolename);
    @Query(value = "select r from Role r where r.name=?1 and r.org.id=?2")
    Role findByNameAndOrgId(String name, String orgId);
    @Query(value = "select r from Role r where r.org.id=?1")
    List<Role> findAllByOrgId(String orgId);
    /**
     * 根据企业类别ID查找角色
     * @param orgCategoryId
     * @return
     */
    @Query("select r from Role r where r.orgCategory.id=?1")
    List<Role> findByOrgCategoryId(String orgCategoryId);
    /**
     * 根据企业ID和企业类别ID查找角色
     * @param orgId
     * @param orgCategoryId
     * @return
     */
    @Query(value = "select distinct r from Role r where r.org.id=?1 or r.orgCategory.id=?2")
    List<Role> findByOrgIdAndOrgCategoryId(String orgId, String orgCategoryId);
}
