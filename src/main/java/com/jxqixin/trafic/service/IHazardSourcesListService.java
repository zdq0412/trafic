package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.HazardSourcesListDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.HazardSourcesList;
import com.jxqixin.trafic.model.Org;
import org.springframework.data.domain.Page;

public interface IHazardSourcesListService extends ICommonService<HazardSourcesList> {
    /**
     * 根据ID删除危险源
     * @param id
     */
    void deleteById(String id);
    /**
     * 分页查找
     * @param hazardSourcesListDto
     * @param org
     * @return
     */
    Page findHazardSourcesLists(HazardSourcesListDto hazardSourcesListDto, Org org);
    /**
     * 新增危险源清单
     * @param hazardSourcesList
     */
    void addHazardSourcesList(HazardSourcesList hazardSourcesList);
    /**
     * 更新危险源清单
     * @param hazardSourcesList
     */
    void updateHazardSourcesList(HazardSourcesList hazardSourcesList);
}
