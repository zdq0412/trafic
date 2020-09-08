package com.jxqixin.trafic.repository;

import com.jxqixin.trafic.model.Category;
import org.springframework.data.jpa.repository.Modifying;
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
    /**
     * 根据父id查找子类别
     * @param pid
     * @return
     */
    @Query("select c from Category c where c.parent.id=?1 and c.deleted=0")
    List<Category> findAvailableByParentId(String pid);
    /**
     * 根据类别查找
     * @param type
     * @return
     */
    @Query("select c from Category c where c.type=?1")
    List<Category> findByType(String type);
    @Modifying
    @Query(nativeQuery = true,value="update Category set deleted=1 where id=?1")
    void deleteCategoryById(String id);
}
