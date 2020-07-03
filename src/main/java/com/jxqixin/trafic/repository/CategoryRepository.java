package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Category;
import org.springframework.data.jpa.repository.Query;

import java.io.Serializable;
import java.util.List;

public interface CategoryRepository<ID extends Serializable> extends CommonRepository<Category,ID> {
    Category findByName(String name);
    /**
     * 根据父id查找子类别
     * @param pid
     * @return
     */
    @Query("select c from Category c where c.parent.id=?1")
    List<Category> findByParentId(String pid);
}
