package com.jxqixin.trafic.service;
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
     * @param nameDto
     * @param org
     * @return
     */
    Page findHazardSourcesLists(NameDto nameDto, Org org);
}
