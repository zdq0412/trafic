package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Position;
import org.springframework.data.domain.Page;

public interface IPositionService extends ICommonService<Position> {
    /**分页查找职位信息
     * @param nameDto
     * @return
     */
    Page findPositions(NameDto nameDto);

    /**
     * 根据id删除职位
     * @param id
     */
    void deleteById(String id);
}
