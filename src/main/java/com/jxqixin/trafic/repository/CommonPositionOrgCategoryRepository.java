package com.jxqixin.trafic.repository;
import com.jxqixin.trafic.model.CommonPosition;
import com.jxqixin.trafic.model.CommonPositionOrgCategory;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.io.Serializable;
import java.util.List;

public interface CommonPositionOrgCategoryRepository<ID extends Serializable> extends CommonRepository<CommonPositionOrgCategory,ID> {
    /**
     * 根据通用职位ID删除记录
     * @param id 通用职位ID
     */
    @Modifying
    @Query(nativeQuery = true,value = "delete from common_position_org_category where common_position_id=?1")
    void deleteByCommonPositionId(String id);
    /**
     * 根据企业类别ID删除记录
     * @param id 企业类别ID
     */
    @Modifying
    @Query(nativeQuery = true,value = "delete from common_position_org_category where org_category_id=?1")
    void deleteByOrgCategoryId(String id);
    @Query(nativeQuery = true,value="select ocf.common_position_id from common_position_org_category ocf where ocf.org_category_id=?1")
    List<String> findIdsByOrgCategoryId(String orgCategoryId);
    /**
     * 根据企业类别ID查找预设职位
     * @param orgCategoryId
     * @return
     */
    @Query("select p.commonPosition from CommonPositionOrgCategory p where p.orgCategory.id=?1")
    List<CommonPosition> findCommonPositionByOrgCategoryId(String orgCategoryId);
}
