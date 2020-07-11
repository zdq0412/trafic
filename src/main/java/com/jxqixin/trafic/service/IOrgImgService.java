package com.jxqixin.trafic.service;

import com.jxqixin.trafic.model.Org;
import com.jxqixin.trafic.model.OrgImg;

import java.util.List;

public interface IOrgImgService extends ICommonService<OrgImg> {
    /**
     * 根据资质文件ID删除
     * @param id
     */
    void deleteById(String id);
    /**
     * 根据企业ID查找企业图片
     * @param org
     * @return
     */
    List<OrgImg> findAll(Org org);

    /**
     * 批量保存企业图片
     * @param orgImgList
     */
    void saveAll(List<OrgImg> orgImgList);
}
