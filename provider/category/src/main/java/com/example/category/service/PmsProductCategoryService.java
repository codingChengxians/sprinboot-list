package com.example.category.service;

import com.example.category.entity.PmsProductCategory;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 产品分类(PmsProductCategory)表服务接口
 *
 * @author luok
 * @since 2020-06-01 18:09:14
 */
public interface PmsProductCategoryService {
    PageInfo<PmsProductCategory> list(String filter, int pageNum, int pageSize);

    void update(PmsProductCategory pmsProductCategory);

    void delete(Long id);

    PmsProductCategory get(Long id);

    List<PmsProductCategory> findByParentIdAndLevel(Long id, int level);

    List<PmsProductCategory> findByChildrenIdAndLevel(Long parentId, int level);

    void add(String name, Long parentId, Integer navStatus, Integer showStatus, String icon, Integer sort, String description);
}