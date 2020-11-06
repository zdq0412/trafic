package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.CommonPositionDto;
import com.jxqixin.trafic.model.CommonPosition;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICommonPositionService extends ICommonService<CommonPosition> {
    /**
     * 分页查询信息
     * @param commonPositionDto
     * @return
     */
    Page findCommonPositions(CommonPositionDto commonPositionDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 查找所有预设职位
     * @return
     */
    List<CommonPosition> findAllCommonPositions();
    /**
     * 根据企业类别ID查找所有预设职位ID
     * @param orgCategoryId
     * @return
     */
    List<String> findIdsByOrgCategoryId(String orgCategoryId);
    /**
     * 将预设职位分配给企业类别
     * @param commonPositionIdArray
     * @param orgCategoryId
     */
    void assign2OrgCategory(String[] commonPositionIdArray, String orgCategoryId);

    /**
     * 根据企业类别ID查找预设职位
     * @param orgCategoryId
     * @return
     */
    List<CommonPosition> findByOrgCategoryId(String orgCategoryId);
}
