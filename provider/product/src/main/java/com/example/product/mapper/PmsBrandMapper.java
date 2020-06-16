package com.example.product.mapper;

import com.example.product.entity.PmsBrand;
import com.example.tkmybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品牌表(PmsBrand)表数据库访问层
 *
 * @author luok
 * @since 2020-06-07 11:06:04
 */

@Mapper
public interface PmsBrandMapper extends MyMapper<PmsBrand>{

}