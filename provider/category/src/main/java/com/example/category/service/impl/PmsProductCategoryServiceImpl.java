package com.example.category.service.impl;

import com.example.category.entity.CategoryTree;
import com.example.category.entity.PmsProductCategory;
import com.example.category.mapper.PmsProductCategoryMapper;
import com.example.category.service.PmsProductCategoryService;
import com.example.tkmybatis.MapperQuery;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 产品分类(PmsProductCategory)表服务实现类
 *
 * @author luok
 * @since 2020-06-01 17:40:03
 */
@Service
public class PmsProductCategoryServiceImpl implements PmsProductCategoryService {

    @Resource
    private PmsProductCategoryMapper pmsProductCategoryMapper;

    @Override
    public PageInfo<PmsProductCategory> list(String filter, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PmsProductCategory> pmsProductCategorys = pmsProductCategoryMapper.selectAll();
        return PageInfo.of(pmsProductCategorys);
    }


    @Override
    public void update(PmsProductCategory pmsProductCategory) {
        if (pmsProductCategory.getParentId() > 0) {
            PmsProductCategory parent = new MapperQuery<>(PmsProductCategory.class)
                    .andEqualTo("id", pmsProductCategory.getParentId())
                    .queryOne(pmsProductCategoryMapper);
            if (parent != null) {
                if (parent.getLevel() > 0) {
                    //todo 父级不存在，报错 父级不可以是自己的id
                }
                pmsProductCategory.setLevel(parent.getLevel() + 1);
            }
        } else {
            pmsProductCategory.setLevel(1);

        }
        pmsProductCategoryMapper.updateByPrimaryKey(pmsProductCategory);
    }


    @Override
    public void delete(Long id) {
        pmsProductCategoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PmsProductCategory get(Long id) {
        return pmsProductCategoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<PmsProductCategory> findByParentIdAndLevel(Long id, int level) {
        List<PmsProductCategory> query = new MapperQuery<>(PmsProductCategory.class)
                .andEqualTo("parentId", id, null != id)
                .andEqualTo("level", level)
                .orderBy("sort", true)
                .query(pmsProductCategoryMapper);
        query.forEach(e -> {
            List<PmsProductCategory> children = hasChildren(e.getId(), e.getLevel() + 1);
            e.setHasChildren(children.size() > 0 ? true : false);
        });
        return query;
    }

    private List<PmsProductCategory> hasChildren(long parentId, int level) {
        return new MapperQuery<>(PmsProductCategory.class)
                .andEqualTo("parentId", parentId)
                .andEqualTo("level", level)
                .query(pmsProductCategoryMapper);
    }

    @Override
    public List<PmsProductCategory> findByChildrenIdAndLevel(Long parentId, int level) {
        PmsProductCategory pmsProductCategory = new MapperQuery<>(PmsProductCategory.class)
                .andEqualTo("id", parentId)
                .andEqualTo("level", level)
                .queryOne(pmsProductCategoryMapper);
        if (pmsProductCategory != null) {
            List<PmsProductCategory> list = new MapperQuery<>(PmsProductCategory.class)
                    .andEqualTo("parentId", pmsProductCategory.getParentId())
                    .andEqualTo("level", pmsProductCategory.getLevel())
                    .orderBy("sort", true)
                    .query(pmsProductCategoryMapper);
            list.forEach(e -> {
                List<PmsProductCategory> children = hasChildren(e.getId(), e.getLevel() + 1);
                e.setHasChildren(children.size() > 0 ? true : false);
            });
            return list;
        }

        return Collections.emptyList();
    }

    public List<Long> getParentIds(Long parentId) {
        List<Long> ids = new ArrayList<>();
        List<Long> parentIds = getParentId(ids, parentId);
        Collections.reverse(parentIds);
        if (parentIds.size() == 0) {
            parentIds.add(0L);
        }
        return parentIds;
    }

    private List<Long> getParentId(List<Long> ids, Long parentId) {
        PmsProductCategory parent = new MapperQuery<>(PmsProductCategory.class)
                .andEqualTo("id", parentId)
                .queryOne(pmsProductCategoryMapper);
        if (parent != null) {
            ids.add(parent.getId());
            if (parent.getParentId() >= 0) {
                getParentId(ids, parent.getParentId());
            }
        }
        return ids;
    }

    public List<CategoryTree> tree() {
        List<PmsProductCategory> pmsProductCategories = pmsProductCategoryMapper.selectAll();
        List<CategoryTree> tree = getTree(pmsProductCategories, 0L);
        return tree;
    }

    private List<CategoryTree> getTree(List<PmsProductCategory> list, Long parentId) {
        List<PmsProductCategory> collect = list.stream().filter(e -> e.getParentId().compareTo(parentId) == 0).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return null;
        }
        List<CategoryTree> tree = new ArrayList<>();
        collect.forEach(e -> {
            tree.add(new CategoryTree().setId(e.getId()).setName(e.getName()).setChildren(getTree(list, e.getId())));
        });
        return tree;
    }

    @Transactional
    @Override
    public void add(String name, Long parentId, Integer navStatus, Integer showStatus, String icon, Integer sort, String description) {
        PmsProductCategory pmsProductCategory = new PmsProductCategory()
                .setName(name)
                .setParentId(parentId)
                .setIcon(icon)
                .setSort(sort)
                .setNavStatus(navStatus)
                .setShowStatus(showStatus)
                .setDescription(description);
        pmsProductCategory.setLevel(1);
        if (parentId > 0) {
            PmsProductCategory parent = pmsProductCategoryMapper.selectByPrimaryKey(parentId);
            pmsProductCategory.setLevel(parent.getLevel() + 1);
        }
        pmsProductCategoryMapper.insert(pmsProductCategory);

    }
}