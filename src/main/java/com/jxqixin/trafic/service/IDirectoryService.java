package com.jxqixin.trafic.service;
import com.jxqixin.trafic.model.Directory;

import java.util.List;

public interface IDirectoryService extends ICommonService<Directory> {
    /**
     * 查找当前模式下的目录
     * @return
     */
    List<Directory> findDirectories();
}
