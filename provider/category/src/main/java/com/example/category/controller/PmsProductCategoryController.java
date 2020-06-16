package com.example.category.controller;


import com.example.category.dto.CategoryQueryChildrenDto;
import com.example.category.dto.CategoryQueryParentDto;
import com.example.category.dto.PmsProductCategoryAddDto;
import com.example.category.entity.CategoryTree;
import com.example.category.entity.PmsProductCategory;
import com.example.category.service.impl.PmsProductCategoryServiceImpl;
import com.example.category.vo.PmsProductCategoryVo;
import com.example.common.PageDto;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.category.dto.PmsProductCategorySearchDto;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 产品分类(PmsProductCategory)表控制层
 *
 * @author luok
 * @since 2020-06-01 17:40:03
 */
@RestController
@RequestMapping("/api/pmsProductCategory")
public class PmsProductCategoryController {
    /**
     * 服务对象
     */
    @Autowired
    private PmsProductCategoryServiceImpl pmsProductCategoryService;

    @GetMapping("/list")
    public PageInfo<PmsProductCategory> selectAll(PmsProductCategorySearchDto dto, PageDto pageDto) {
        return this.pmsProductCategoryService.list(dto.getFilter(), pageDto.getPageNum(), pageDto.getPageSize());
    }

    @GetMapping("/tree")
    public List<CategoryTree> tree() {
        return this.pmsProductCategoryService.tree();
    }

    @GetMapping("/get")
    public PmsProductCategoryVo get(@RequestParam(value = "id") long id) {
        PmsProductCategory pmsProductCategory = this.pmsProductCategoryService.get(id);
        PmsProductCategoryVo vo = new PmsProductCategoryVo();
        BeanUtils.copyProperties(pmsProductCategory, vo);
        vo.setParentId(this.pmsProductCategoryService.getParentIds(pmsProductCategory.getParentId()));
        return vo;
    }

    @GetMapping("/findByParentIdAndLevel")
    public List<PmsProductCategory> findByParentIdOrLevel(CategoryQueryChildrenDto dto) {
        return this.pmsProductCategoryService.findByParentIdAndLevel(dto.getId(), dto.getLevel());
    }

    @GetMapping("/findByChildrenIdAndLevel")
    public List<PmsProductCategory> findByChildrenIdAndLevel(CategoryQueryParentDto dto) {
        return this.pmsProductCategoryService.findByChildrenIdAndLevel(dto.getParentId(), dto.getLevel());
    }

    @PostMapping("/add")
    public void insert(@RequestBody @Validated PmsProductCategoryAddDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldError());
        }
        this.pmsProductCategoryService.add(dto.getName(), dto.getParentId(), dto.getNavStatus(), dto.getShowStatus(),
                dto.getIcon(), dto.getSort(), dto.getDescription());
    }

    @PostMapping("/update")
    public void update(@RequestBody PmsProductCategory pmsProductCategory) {
        this.pmsProductCategoryService.update(pmsProductCategory);
    }

    @RequestMapping("/delete")
    public void delete(Long id) {
        this.pmsProductCategoryService.delete(id);
    }

}