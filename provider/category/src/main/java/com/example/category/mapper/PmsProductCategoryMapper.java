package com.example.category.mapper;

import com.example.category.entity.PmsProductCategory;
import com.example.tkmybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品分类(PmsProductCategory)表数据库访问层
 *
 * @author luok
 * @since 2020-06-01 17:40:03
 */

@Mapper
public interface PmsProductCategoryMapper extends MyMapper<PmsProductCategory>{

}