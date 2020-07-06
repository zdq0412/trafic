package com.jxqixin.trafic.service;
import com.jxqixin.trafic.dto.NameDto;
import com.jxqixin.trafic.model.Directory;
import com.jxqixin.trafic.model.Functions;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDirectoryService extends ICommonService<Directory> {
    /**
     * 查找当前模式下的目录
     * @return
     */
    List<Directory> findDirectories();
    /**
     * 分页查找目录信息
     * @param nameDto
     * @return
     */
    Page findDirectorys(NameDto nameDto);
    /**
     * 根据名称查找目录
     * @param name 目录名称
     * @return
     */
    Directory findByName(String name);
    /**
     * 根据ID删除目录及目录下的菜单
     * @param id
     */
    void deleteById(String id);
    /**
     * 为目录分配菜单
     * @param functionIdArray
     * @param dirId
     */
    void assign2Directory(String[] functionIdArray, String dirId);
}
