package com.jxqixin.trafic.service;

import com.jxqixin.trafic.dto.IDCardDto;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.IDCard;
import org.springframework.data.domain.Page;

public interface IIDCardService extends ICommonService<IDCard> {
    /**
     * 分页查询
     * @param idCardDto
     * @return
     */
    Page findIDCards(IDCardDto idCardDto);
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);
}
