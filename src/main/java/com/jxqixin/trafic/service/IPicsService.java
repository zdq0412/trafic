package com.jxqixin.trafic.service;
import com.jxqixin.trafic.model.Pics;

import java.util.List;

public interface IPicsService extends ICommonService<Pics> {
    /**
     * 根据ID删除
     * @param id
     */
    void deleteById(String id);

    /**
     * 根据类别和类别记录ID查找照片
     * @param type 类别(MEETING,TRAINING)
     * @param pid 类别记录ID(会议表记录ID、培训表记录ID等)
     * @return
     */
    List<Pics> findPics(String type, String pid);
    /**
     * 根据类别和类别ID删除所有
     * @param type
     * @param pid
     */
    void deleteAll(String type, String pid);
}
