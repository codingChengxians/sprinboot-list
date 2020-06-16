package com.example.product.mapper;

import com.example.product.entity.PmsProduct;
import com.example.tkmybatis.MyMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品信息(PmsProduct)表数据库访问层
 *
 * @author luok
 * @since 2020-06-02 15:11:02
 */

@Mapper
public interface PmsProductMapper extends MyMapper<PmsProduct>{

}